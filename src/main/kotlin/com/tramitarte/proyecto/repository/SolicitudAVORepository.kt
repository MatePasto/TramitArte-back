package com.tramitarte.proyecto.repository

import com.tramitarte.proyecto.dominio.SolicitudAVO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SolicitudAVORepository: JpaRepository<SolicitudAVO, Long> {}