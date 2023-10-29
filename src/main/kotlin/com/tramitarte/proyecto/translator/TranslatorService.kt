package com.tramitarte.proyecto.translator


import com.tramitarte.proyecto.dominio.Documentacion
import com.tramitarte.proyecto.service.TramiteService
import com.tramitarte.proyecto.tesseract.TesseractService
import me.bush.translator.Language
import me.bush.translator.Translator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File

import java.io.FileWriter
import java.io.InputStream
import java.util.Base64

@Service
class TranslatorService {

    @Autowired
    lateinit var tesseractService: TesseractService
    @Autowired
    lateinit var tramiteService: TramiteService

    @Transactional
    fun translatePDF(file: InputStream): String {
        val text = tesseractService.recognizedPDF(file)
        val translator = Translator()
        val translation = translator.translateBlocking(text, Language.ITALIAN, Language.AUTO)
        return translation.translatedText
    }

    @Transactional
    fun createFileTXT(fileNames: List<String?>, files: List<InputStream>){
        try {
            for ((index, file) in files.withIndex()) {
                val translatedText = translatePDF(file)
                val fileName = "C:/Users/Usuario/Desktop/pdf/${fileNames[index]}-traducido.txt"
                val fileTXT = FileWriter(fileName)
                fileTXT.write(translatedText)
                fileTXT.close()
            }
        } catch (exception: Exception) {
            throw exception
        }
    }

    @Transactional
    fun createFileTXTFromEncodedFile(id: Long, files: List<Documentacion>){
        try {
            val fileTranslatedList = mutableListOf<Documentacion>()
            for ((index, file) in files.withIndex()) {
                val fileDecoded = decodeBase64ToFile(file)
                val translatedText = translatePDF(fileDecoded.inputStream())
                val fileName = "${file.nombre}-traducido.txt"
                val fileTXT = File(fileName)
                fileTXT.writeText(translatedText)
                val fileEncoded = encodeFileToBase64(fileName, fileTXT)
                fileTranslatedList.add(fileEncoded)
                fileTXT.delete()
            }
            // no se va a usar la traduccion porque no funciona bien con tesseract, si fueran pdf con texto y no con imagenes seria ideal.
           // tramiteService.cargarDocumentacionTraducida(id, fileTranslatedList)
        } catch (exception: Exception) {
            throw exception
        }
    }

    @Transactional
    fun createFileTXTFromEncodedFilePrueba(files: List<Documentacion>): List<Documentacion> {
        try {
            val fileTranslatedList = mutableListOf<Documentacion>()
            for ((index, file) in files.withIndex()) {
                val fileDecoded = decodeBase64ToFile(file)
                val translatedText = translatePDF(fileDecoded.inputStream())
                val fileName = "${file.nombre}-traducido.txt"
                val fileTXT = File(fileName)
                fileTXT.writeText(translatedText)
                val fileEncoded = encodeFileToBase64(fileName, fileTXT)
                fileTranslatedList.add(fileEncoded)
                fileTXT.delete()
            }
            return fileTranslatedList
        } catch (exception: Exception) {
            throw exception
        }
    }

    @Transactional
    fun codificar(file: InputStream): String{
        val fileBytes = file.readAllBytes()
        return Base64.getEncoder().encodeToString(fileBytes)
    }

    private fun encodeFileToBase64(fileName: String, file: File): Documentacion {
        val fileContent = file.readText()
        val base64String = Base64.getEncoder().encodeToString(fileContent.toByteArray())
        return Documentacion(fileName, base64String, "")
    }

    private fun decodeBase64ToFile(base64String: Documentacion): ByteArray {
        val fileBytes = Base64.getDecoder().decode(base64String.archivoBase64)
        return fileBytes
    }

}