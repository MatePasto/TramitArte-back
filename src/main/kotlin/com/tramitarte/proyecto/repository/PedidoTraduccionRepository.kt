package com.tramitarte.proyecto.repository

import com.tramitarte.proyecto.dominio.PedidoTraduccion
import com.tramitarte.proyecto.dominio.Tramite
import com.tramitarte.proyecto.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoTraduccionRepository: JpaRepository<PedidoTraduccion, Long> {

    fun findByTraductor(traductor: Usuario?): List<PedidoTraduccion?>

    fun findByTramite(tramite: Tramite?): List<PedidoTraduccion?>
}