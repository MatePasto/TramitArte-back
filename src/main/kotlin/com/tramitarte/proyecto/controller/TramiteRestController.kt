package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.dominio.*
import com.tramitarte.proyecto.service.DocumentacionService
import com.tramitarte.proyecto.service.SolicitudAVOService
import com.tramitarte.proyecto.service.TramiteService
import com.tramitarte.proyecto.service.UsuarioService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.io.IOException
import java.net.URLDecoder
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


@RestController
@CrossOrigin("*")
@RequestMapping("/api")
class TramiteRestController {

    @Autowired
    lateinit var tramiteService: TramiteService

    @Autowired
    lateinit var solicitudAVOService: SolicitudAVOService

    @Autowired
    lateinit var usuarioService: UsuarioService


    @Autowired
    lateinit var documentacionService: DocumentacionService

    val zipFileName = "Documentación-trámite"


    @GetMapping("/tramite/usuario/{idUsuario}")
    fun buscarTramitePorUsuario(@PathVariable idUsuario: Long): ResponseEntity<Tramite?> {
        try {
            val usuario = usuarioService.buscarPorId(idUsuario)
            return ResponseEntity(tramiteService.buscarPorUsuario(usuario), HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/tramite/{idUsuario}")
    fun iniciarTramite(@PathVariable idUsuario: Long): ResponseEntity<Tramite> {
        //buscar usuario a partir del logueado y sumarlo a su lista de trámites
        try {
            var tramiteIniciado = tramiteService.iniciarTramite(idUsuario)
            return ResponseEntity(tramiteIniciado, HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/carga-avo/{id}")
    fun cargarAVO(@PathVariable id: Long, @RequestBody solicitud: SolicitudAVO): ResponseEntity<SolicitudAVO> {
        try {
            var avo = solicitudAVOService.guardar(id, solicitud)
            return ResponseEntity(avo, HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/carga/documentacion/usuario/{id}")
    fun cargaDocumentacionUsuario(
        @PathVariable id: Long,
        @RequestBody documentacionUsuario: MutableList<Documentacion>
    ): ResponseEntity<String> {
        try {
            tramiteService.cargaDocumentacionUsuario(id, documentacionUsuario)
            return ResponseEntity("Documentación guardada con éxito", HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/carga/documentacion/avo/{id}")
    fun cargaDocumentacionAVO(
        @PathVariable id: Long,
        @RequestBody documentacionAVO: MutableList<Documentacion>
    ): ResponseEntity<String> {
        try {
            tramiteService.cargaDocumentacionAVO(id, documentacionAVO)
            return ResponseEntity("Documentación guardada con éxito", HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/carga/documentacion/descendientes/{id}")
    fun cargaDocumentacionDescendientes(
        @PathVariable id: Long,
        @RequestBody documentacionDescendientes: MutableList<Documentacion>
    ): ResponseEntity<String> {
        try {
            val tramite = tramiteService.cargaDocumentacionDescendientes(id, documentacionDescendientes)
            return ResponseEntity("Documentación guardada con éxito", HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }


    @PostMapping("/carga/documentacion/traducida/{id}")
    fun cargaDocumentacionTraducida(
            @PathVariable id: Long,
            @RequestBody documentacionTraducida: MutableList<Documentacion>
    ): Tramite {
        try {
            val tramite = tramiteService.cargarDocumentacionTraducida(id, documentacionTraducida)
            return tramite
        } catch (exception: IllegalArgumentException) {
            throw exception
        }
    }

        @GetMapping("/documentacion/{id}")
        fun mostrarDocumentacion(@PathVariable id: Long): ResponseEntity<DocumentListDTO> {
            try {
                return tramiteService.mostrarDocumentacion(id)
            } catch (exception: IllegalArgumentException) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
            }
        }


        @PostMapping("/modificar/documento/{id}")
        fun modificarDocumento(
                @PathVariable id: Long,
                @RequestBody documento: Documentacion
        ): ResponseEntity<String> {
            try {
                val doc = documentacionService.modificar(id, documento)
                return ResponseEntity("Documentación guardada con éxito", HttpStatus.OK)
            } catch (exception: IllegalArgumentException) {


                throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
            }
        }

        @PostMapping("/avanzar-etapa/{id}")
        fun avanzarEtapa(@PathVariable id: Long): ResponseEntity<Etapa> {
            try {
                return ResponseEntity.ok(tramiteService.avanzarEtapa(id))
            } catch (exception: IllegalArgumentException) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
            }
        }

        @DeleteMapping("/tramite/{id}")
        fun eliminar(@PathVariable id: Long): ResponseEntity<String> {
            try {
                tramiteService.eliminar(id)
                return ResponseEntity("Trámite eliminado exitosamente", HttpStatus.OK)
            } catch (exception: IllegalArgumentException) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
            }
        }

        @GetMapping("/solicitud/usuario/{idUsuario}")
        fun buscarAVOPorUsuario(@PathVariable idUsuario: Long): ResponseEntity<SolicitudAVO?> {
            try {
                val usuario = usuarioService.buscarPorId(idUsuario)

                return ResponseEntity(usuario?.let { solicitudAVOService.buscarAVOPorUsuario(it) }, HttpStatus.OK)
            } catch (exception: IllegalArgumentException) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
            }
        }
    }