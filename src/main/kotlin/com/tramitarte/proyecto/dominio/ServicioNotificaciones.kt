package com.tramitarte.proyecto.dominio

import com.tramitarte.proyecto.repository.NotificacionRepository
import com.tramitarte.proyecto.repository.SolicitudTraduccionRepository
import com.tramitarte.proyecto.repository.TramiteRepository
import com.tramitarte.proyecto.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ServicioNotificaciones {
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    lateinit var notificacionRepository: NotificacionRepository
    @Autowired
    lateinit var tramiteRepository: TramiteRepository
    @Autowired
    lateinit var solicitudTraduccionRepository: SolicitudTraduccionRepository

    @Transactional
    fun generarAlertaATraductor(idOrigen: Long, idDestino: Long, descripcion: String) {
        val usuarioOrigen = usuarioRepository.findById(idOrigen).get()
        val usuarioDestino = usuarioRepository.findById(idDestino).get()
        var alertaNueva = Alerta(usuarioOrigen, usuarioDestino, descripcion)
        val solicitudTraduccion = SolicitudTraduccion(usuarioOrigen, usuarioDestino)
        solicitudTraduccionRepository.save(solicitudTraduccion)
        notificacionRepository.save(alertaNueva)
    }

    @Transactional
    fun generarAlerta(idOrigen: Long, idDestino: Long, descripcion: String) {
        val usuarioOrigen = usuarioRepository.findById(idOrigen).get()
        val usuarioDestino = usuarioRepository.findById(idDestino).get()
        val alertaNueva = Alerta(usuarioOrigen, usuarioDestino, descripcion)
        notificacionRepository.save(alertaNueva)
    }

    @Transactional
    fun eliminarSolicitudTraduccion(idSolicitud: Long){
        solicitudTraduccionRepository.deleteById(idSolicitud)
    }

    @Transactional
    fun borrarSolicitudTraduccionPorSolicitante(idSolicitante: Long){
        val solicitante = usuarioRepository.findById(idSolicitante).get()
        val traductorDeSolicitud = solicitudTraduccionRepository.findBySolicitante(solicitante)!!.traductor
        solicitante.id?.let { traductorDeSolicitud.id?.let { it1 -> generarAlerta(it, it1, "El solicitante "+solicitante.correoElectronico+" ha eliminado su solicitud") } }
        solicitudTraduccionRepository.deleteBySolicitante(solicitante)
    }
}
