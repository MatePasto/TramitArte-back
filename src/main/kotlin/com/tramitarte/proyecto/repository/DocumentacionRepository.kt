package com.tramitarte.proyecto.repository

import com.tramitarte.proyecto.dominio.Documentacion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentacionRepository: JpaRepository<Documentacion, Long> {}