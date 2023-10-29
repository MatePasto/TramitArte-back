package com.tramitarte.proyecto.repository

import com.tramitarte.proyecto.dominio.Etapa
import org.springframework.data.jpa.repository.JpaRepository

interface EtapaRepository: JpaRepository<Etapa, Long> {
}