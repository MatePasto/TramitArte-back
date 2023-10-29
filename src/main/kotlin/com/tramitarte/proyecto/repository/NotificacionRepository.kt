package com.tramitarte.proyecto.repository

import com.tramitarte.proyecto.dominio.Notificacion
import com.tramitarte.proyecto.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificacionRepository: JpaRepository<Notificacion, Long> {
    fun findAllByUsuarioDestino(usuario: Usuario): List<Notificacion>
}
