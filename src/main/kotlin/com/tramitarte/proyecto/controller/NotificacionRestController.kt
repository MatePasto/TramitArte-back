package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.dominio.Alerta
import com.tramitarte.proyecto.dominio.Notificacion
import com.tramitarte.proyecto.dominio.ServicioNotificaciones
import com.tramitarte.proyecto.dominio.Usuario
import com.tramitarte.proyecto.repository.NotificacionRepository
import com.tramitarte.proyecto.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.Optional

@RestController
@CrossOrigin
@RequestMapping("/api")
class NotificacionRestController {
    @Autowired
    lateinit var notificacionRepository: NotificacionRepository
    @Autowired
    lateinit var servicioNotificaciones: ServicioNotificaciones

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @GetMapping("/notificacion")
    fun buscarNotificaciones(
        @RequestParam usuario: Optional<Usuario>
    ) {
        try {
            notificacionRepository.findAllByUsuarioDestino(usuario.get())
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/notificacion/alerta-traductor/{idOrigen}/{idDestino}")
    fun crearAlertaATraductor(
        @PathVariable idOrigen: Long,
        @PathVariable idDestino: Long,
        @RequestParam descripcion: String
    ) {
        try {
            servicioNotificaciones.generarAlertaATraductor(idOrigen, idDestino, descripcion)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/notificacion/alerta/{idOrigen}/{idDestino}")
    fun crearAlerta(
            @PathVariable idOrigen: Long,
            @PathVariable idDestino: Long,
            @RequestParam descripcion: String
    ){
        try {
            servicioNotificaciones.generarAlerta(idOrigen, idDestino, descripcion)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @DeleteMapping("/notificacion/alerta/{id}")
    fun borrarAlerta(
        @PathVariable id: Long
    ) =
        notificacionRepository.deleteById(id)

    @DeleteMapping("/notificacion/solicitud/{id}")
    fun borrarSolicitudTraduccion(
            @PathVariable id: Long
    ) {
        servicioNotificaciones.eliminarSolicitudTraduccion(id)
    }

    @DeleteMapping("/notificacion/solicitud/solicitante/{id}")
    fun borrarSolicitudTraduccionPorSolicitante(
            @PathVariable id: Long
    ) {
        servicioNotificaciones.borrarSolicitudTraduccionPorSolicitante(id)
    }
}
