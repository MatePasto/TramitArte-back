package com.tramitarte.proyecto.translator

import com.tramitarte.proyecto.dominio.Documentacion
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
class TranslatorController {

    @Autowired
    lateinit var translatorService: TranslatorService

    @PostMapping("/translate-italian")
    fun translateItalian(@RequestParam file: MultipartFile): String = translatorService.translatePDF(file.inputStream)

    @PostMapping("/create-txt")
    fun createTXT(@RequestParam file: List<MultipartFile>){ translatorService.createFileTXT(file.map { it.originalFilename }, file.map { it.inputStream }) }

    //esta es el endpoint posta
    @PostMapping("/send/user/{id}/file-translated")
    fun createTXTFromEncodedFile(@RequestParam id: Long, @RequestBody file: List<Documentacion>) {translatorService.createFileTXTFromEncodedFile(id, file)}

    @PostMapping("/send/user/file-translated")
    fun createTXTFromEncodedFilePrueba(@RequestBody file: List<Documentacion>): List<Documentacion> = translatorService.createFileTXTFromEncodedFilePrueba(file)

    @PostMapping("/codificar")
    fun codificar(@RequestParam file: MultipartFile): String = translatorService.codificar(file.inputStream)
}