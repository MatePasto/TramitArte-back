package com.tramitarte.proyecto.tesseract

import io.ktor.utils.io.core.*
import net.sourceforge.tess4j.Tesseract
import org.apache.pdfbox.io.IOUtils
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.pdfbox.tools.imageio.ImageIOUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream
import javax.imageio.ImageIO
import kotlin.io.use

@Service
class TesseractService {
    @Autowired
    lateinit var tesseract: Tesseract

    val phraseCertificate = "registro del estado civil y capacidad de las personas"
    val phraseCertificate2 = "registro provincial de las personas"
    val phraseBirthCertificate = "nacimiento"
    val phraseMarriageCertificate = "matrimonio"
    val phraseDeathCertificate1 = "defuncion"
    val phraseDeathCertificate2 = "fallecio"

    val phraseCertificateItaly = "stato civile"
    val phraseCertificateItaly2 = "Ufficio dello Stato Civile"
    val phraseBirthItalyCertificate = "nascita"
    val phraseDeathItalyCertificate = "certificato di morte"
    val phraseDeathItalyCertificate2 = "morte"

    fun recognizedImage(inputStream: InputStream): String{
        try {
            val image: BufferedImage = ImageIO.read(inputStream)
            return tesseract.doOCR(image)
        }catch (exception: Exception){
            throw exception
        }
    }

    fun recognizedPDF(file: InputStream): String{
        try {
            var text = ""
            PDDocument.load(file).use { pdDocument ->
                val pdfStripper = PDFTextStripper()
                text = pdfStripper.getText(pdDocument)
            }
            return text
        }catch (exception: Exception){
            throw exception
        }
    }

    fun extractImagesFromPDFWithOCR(inputStream: InputStream): String {
        try {
            val document = PDDocument.load(inputStream)
            val pdfRenderer = PDFRenderer(document)
            var result = ""

            for (pageIndex in 0 until document.numberOfPages) {
                val image = pdfRenderer.renderImageWithDPI(pageIndex, 300f, ImageType.RGB)

                // Aplicar Tesseract a la imagen
                result += tesseract.doOCR(image)

                // No es necesario eliminar la imagen en este caso, ya que no estamos escribiendo archivos intermedios

                // Cerramos el documento solo cuando hayamos terminado de procesar todas las páginas
                if (pageIndex == document.numberOfPages - 1) {
                    document.close()
                }
            }
            return result
        } catch (exception: Exception) {
            throw exception
        }
    }

    fun isDniFrente(inputStream: InputStream): Boolean{
        val text = recognizedImage(inputStream)
        return containsPhraseFrente(text)
    }

    fun isDniDorso(inputStream: InputStream): Boolean{
        val text = recognizedImage(inputStream)
        return containsPhraseDorso(text)
    }

    fun isCertificate(file: InputStream): Boolean {
        val text = extractImagesFromPDFWithOCR(file)
        return containsPhrasePDF(text)
    }

    fun isMarriageCertificate(file: InputStream): Boolean {
        val text = extractImagesFromPDFWithOCR(file)
        val textMin = text.lowercase()
        return containsPhrasePDFMarriage(textMin)
    }

    fun isBirthCertificate(file: InputStream): Boolean {
        val text = extractImagesFromPDFWithOCR(file)
        val textMin = text.lowercase()
        return containsPhrasePDFBirth(textMin)
    }

    fun isDeathCertificate(file: InputStream): Boolean {
        val text = extractImagesFromPDFWithOCR(file)
        val textMin = text.lowercase()
        return containsPhrasePDFDeath(textMin)
    }

    fun isBirthCertificateItaly(file: InputStream): Boolean {
        val text = extractImagesFromPDFWithOCR(file)
        val textMin = text.lowercase()
        return containsPhrasePDFBirthItaly(textMin)
    }

    fun isMarriageCertificateItaly(file: InputStream): Boolean {
        val text = extractImagesFromPDFWithOCR(file)
        val textMin = text.lowercase()
        return containsPhrasePDFMarriageItaly(textMin)
    }

    fun isDeathCertificateItaly(file: InputStream): Boolean {
        val text = extractImagesFromPDFWithOCR(file)
        val textMin = text.lowercase()
        return containsPhrasePDFDeathItaly(textMin)
    }

    private fun containsPhrasePDFDeathItaly(text: String): Boolean{
        return ((text.contains(phraseDeathItalyCertificate) || text.contains(phraseDeathItalyCertificate2)))
    }

    private fun containsPhrasePDFMarriageItaly(text: String): Boolean{
        return (text.contains(phraseMarriageCertificate) && (text.contains(phraseCertificateItaly) || text.contains(phraseCertificateItaly2)))
    }

    private fun containsPhrasePDFBirthItaly(text: String): Boolean{
        return (text.contains(phraseBirthItalyCertificate) && (text.contains(phraseCertificateItaly) || text.contains(phraseCertificateItaly2)))
    }

    private fun containsPhrasePDFDeath(text: String): Boolean{
        return ((text.contains(phraseDeathCertificate1) || text.contains(phraseDeathCertificate2)))
    }

    private fun containsPhrasePDFBirth(text: String): Boolean{
        return (text.contains(phraseBirthCertificate) && (text.contains(phraseCertificate) || text.contains(phraseCertificate2)))
    }

    private fun containsPhrasePDFMarriage(text: String): Boolean{
        return (text.contains(phraseMarriageCertificate) && (text.contains(phraseCertificate) || text.contains(phraseCertificate2)))

    }

    private fun containsPhrasePDF(text: String): Boolean{
        return (text.contains(phraseBirthCertificate) && (text.contains(phraseCertificate) || text.contains(phraseCertificate2)))
                || (text.contains(phraseMarriageCertificate) && (text.contains(phraseCertificate) || text.contains(phraseCertificate2)))
                || ((text.contains(phraseDeathCertificate1) && text.contains(phraseDeathCertificate2)) && (text.contains(phraseCertificate) || text.contains(phraseCertificate2)))
    }

    // Como los dni tienen un dibujo por detras de los caracteres, tesseract no puede distinguir a veces bien los
    // caracteres, esto va a depender del angulo y calidad de la foto del dni, como minimo se tienen que registrar
    // algunas de las siguientes frases o patrones
    private fun containsPhraseFrente(text: String): Boolean {
        val textMin = text.lowercase()
        val phraseMinFrente = "registro nacional de las personas"

        return textMin.contains(phraseMinFrente)
    }

    private fun containsPhraseDorso(text: String): Boolean {
        val textMin = text.lowercase()
        val phraseMinDorso = "ministro del interior y transporte"
        val pattern = findPattern(text)

        return (textMin.contains(phraseMinDorso) || pattern.isNotEmpty())
    }

    private fun findPattern(text: String): List<String> {
        // Con este patron nos fijamos si la imagen contiene "idarg" seguido de caracteres o numeros sin importar
        // mayusculas o minusculas, ejemplo: "idarg2324sdf". Lo mismo con "arg" pero al reves, ejemplo: "234rearg"
        val regex = Regex("(?i)(idarg\\w+|arg\\w+)")
        return regex.findAll(text).map { it.value }.toList()
    }
}