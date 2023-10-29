package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.dominio.Rol
import com.tramitarte.proyecto.dominio.SolicitudTraduccion
import com.tramitarte.proyecto.dominio.UpdateUserDTO
import com.tramitarte.proyecto.dominio.Usuario
import com.tramitarte.proyecto.repository.UsuarioRepository
import com.tramitarte.proyecto.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
class UsuarioRestController {
    @Autowired
    lateinit var usuarioService: UsuarioService
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @GetMapping("/usuario/traductores")
    fun buscarTraductores(): List<Usuario> {
        return usuarioService.buscarTraductores()
    }


    @GetMapping("/usuario/solicitante")
    fun buscarSolicitantes(): List<Usuario> {
        var list = usuarioService.buscarPorRol(Rol.TRADUCTOR).stream()
        return list.filter { usuario -> usuario.nesecitaTraduccion }.collect(Collectors.toList())
    }

    @GetMapping("/usuario/precio")
    fun buscarUsuarioPrecio(
        @RequestParam nombre: Optional<String>, @RequestParam apellido: Optional<String>, @RequestParam precio: Optional<Float>): Usuario =
        usuarioService.buscarPorNombreYPrecio(nombre, apellido, precio)

    @GetMapping("/usuario/{id}/notificaciones")
    fun buscarNotificaciones(@PathVariable id: Long) =
        usuarioService.buscarNotificaciones(id)

    @PostMapping("/usuario")
    fun crear(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        try {
            return ResponseEntity.ok(usuarioService.crear(usuario))
        } catch (illegalArgumentExceptcion: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, illegalArgumentExceptcion.message)
        }
    }

    @PostMapping("/usuario/{id}")
    fun update(@PathVariable id: Long, @RequestBody usuario: UpdateUserDTO): ResponseEntity<Usuario> {
        try {
            return ResponseEntity.ok(usuarioService.actualizar(id, usuario))
        } catch (illegalArgumentExceptcion: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, illegalArgumentExceptcion.message)
        }
    }

    @GetMapping("/usuario/{id}")
    fun traerPorId(@PathVariable id: Long): Usuario{
        return usuarioRepository.findById(id).get()
    }

    @GetMapping("/usuario")
    fun buscarPorCorreoElectronico(@RequestParam correoElectronico: String): ResponseEntity<Usuario> {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorCorreoElectronico(correoElectronico))
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @GetMapping("/usuario/{id}/solicitud-traduccion")
    fun buscarSolicitudTraduccion(@PathVariable id: Long): List<SolicitudTraduccion?>{
        return usuarioService.buscarSolicitudTraduccion(id)
    }

    @GetMapping("/usuario/solicitud-traduccion/solicitante/{idSolicitante}/traductor/{idTraductor}")
    fun buscarSolicitudTraduccionSolicitanteYTraductor(@PathVariable idSolicitante: Long, @PathVariable idTraductor: Long): List<SolicitudTraduccion?>{
        return usuarioService.buscarSolicitudTraduccionSolicitanteYTraductor(idSolicitante, idTraductor)
    }

    @GetMapping("/usuario/solicitud/solicitante/{idSolicitante}")
    fun buscarSolicitudPorSolicitante(@PathVariable idSolicitante: Long): SolicitudTraduccion?{
        return usuarioService.buscarSolicitudPorSolicitante(idSolicitante)
    }

    @GetMapping("/usuario/traductor-correo")
    fun buscarTraductorPorCorreo(@RequestParam correoElectronico: String): List<Usuario> {
        try {
            return usuarioService.buscarTraductorPorCorreo(correoElectronico)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }
}
