package com.tramitarte.proyecto.tesseract

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File

@RestController
@CrossOrigin("*")
class TesseractController {
    @Autowired
    lateinit var tesseractService: TesseractService

    @PostMapping("/api/ocr/image")
    fun recognizedImage(@RequestParam img: MultipartFile): String = tesseractService.recognizedImage(img.inputStream)

    @PostMapping("/api/ocr/pdf")
    fun recognizedPDF(@RequestParam file: MultipartFile): String = tesseractService.recognizedPDF(file.inputStream)

    @PostMapping("/api/ocr/image/is_dni_frente")
    fun isDniFrente(@RequestParam img: MultipartFile): Boolean = tesseractService.isDniFrente(img.inputStream)

    @PostMapping("/api/ocr/image/is_dni_dorso")
    fun isDniDorso(@RequestParam img: MultipartFile): Boolean = tesseractService.isDniDorso(img.inputStream)

    @PostMapping("api/ocr/pdf/is_certificate")
    fun isCertificate(@RequestParam pdf: MultipartFile): Boolean = tesseractService.isCertificate(pdf.inputStream)

    @PostMapping("api/ocr/pdf/text_from_img")
    fun recognizedImagesInPDF(@RequestParam pdf: MultipartFile): String = tesseractService.extractImagesFromPDFWithOCR(pdf.inputStream)

    @PostMapping("api/ocr/pdf/is_marriage")
    fun isMarriageCertificate(@RequestParam pdf: MultipartFile): Boolean = tesseractService.isMarriageCertificate(pdf.inputStream)

    @PostMapping("api/ocr/pdf/is_birth")
    fun isBirthCertificate(@RequestParam pdf: MultipartFile): Boolean = tesseractService.isBirthCertificate(pdf.inputStream)

    @PostMapping("api/ocr/pdf/is_death")
    fun isDeathCertificate(@RequestParam pdf: MultipartFile): Boolean = tesseractService.isDeathCertificate(pdf.inputStream)

    @PostMapping("api/ocr/pdf/is_birth_italy")
    fun isBirthCertificateItaly(@RequestParam pdf: MultipartFile): Boolean = tesseractService.isBirthCertificateItaly(pdf.inputStream)

    @PostMapping("api/ocr/pdf/is_marriage_italy")
    fun isMarriageCertificateItaly(@RequestParam pdf: MultipartFile): Boolean = tesseractService.isMarriageCertificateItaly(pdf.inputStream)

    @PostMapping("api/ocr/pdf/is_death_italy")
    fun isDeathCertificateItaly(@RequestParam pdf: MultipartFile): Boolean = tesseractService.isDeathCertificateItaly(pdf.inputStream)
}