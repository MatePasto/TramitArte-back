package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.PedidoTraduccion
import com.tramitarte.proyecto.repository.PedidoTraduccionRepository
import com.tramitarte.proyecto.repository.TramiteRepository
import com.tramitarte.proyecto.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PedidoTraduccionService {
    @Autowired
    lateinit var pedidoTraduccionRepository: PedidoTraduccionRepository
    @Autowired
    lateinit var tramiteRepository: TramiteRepository
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Transactional
    fun crearPedidoTraduccion(idSolicitante: Long, idTraductor: Long){
        val solicitante = usuarioRepository.findById(idSolicitante).get()
        val tramite = tramiteRepository.findByUsuario(solicitante)
        val traductor = usuarioRepository.findById(idTraductor).get()
        val pedido = PedidoTraduccion(tramite, traductor)
        pedidoTraduccionRepository.save(pedido)
    }

    @Transactional
    fun buscarPorTraductor(idTraductor: Long): List<PedidoTraduccion?>{
        val traductor = usuarioRepository.findById(idTraductor).get()
        return pedidoTraduccionRepository.findByTraductor(traductor)
    }

    @Transactional
    fun buscarPorTramite(idTramite: Long): List<PedidoTraduccion?>{
        val tramite = tramiteRepository.findById(idTramite).get()
        return pedidoTraduccionRepository.findByTramite(tramite)
    }
}