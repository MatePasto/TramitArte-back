package com.tramitarte.proyecto.repository

import com.tramitarte.proyecto.dominio.Tramite
import com.tramitarte.proyecto.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TramiteRepository: JpaRepository<Tramite, Long> {
    fun findByUsuario(usuario: Usuario?): Tramite?
}
