package com.tramitarte.proyecto.repository

import com.tramitarte.proyecto.dominio.SolicitudDescarga
import com.tramitarte.proyecto.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SolicitudDescargaRepository: JpaRepository<SolicitudDescarga, Long> {

    fun findBySolicitante(solicitante: Usuario?): List<SolicitudDescarga?>
}