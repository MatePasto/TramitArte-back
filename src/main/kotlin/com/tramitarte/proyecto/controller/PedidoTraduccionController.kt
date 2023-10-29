package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.dominio.PedidoTraduccion
import com.tramitarte.proyecto.dominio.SolicitudTraduccion
import com.tramitarte.proyecto.repository.PedidoTraduccionRepository
import com.tramitarte.proyecto.service.PedidoTraduccionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
class PedidoTraduccionController {
    @Autowired
    lateinit var pedidoTraduccionService: PedidoTraduccionService
    @Autowired
    lateinit var pedidoTraduccionRepository: PedidoTraduccionRepository

    @PostMapping("/pedido/solicitante/{idSolicitante}/traductor/{idTraductor}")
    fun crearPedido(@PathVariable idSolicitante: Long, @PathVariable idTraductor: Long){
        try{
            return pedidoTraduccionService.crearPedidoTraduccion(idSolicitante, idTraductor)
        }catch(e: Exception) {
            throw IllegalArgumentException("No se puede crear este pedido",e)
        }
    }

    @GetMapping("/pedido/traductor/{id}")
    fun buscarPedidoPorTraductor(@PathVariable id: Long): List<PedidoTraduccion?>{
        try{
            return pedidoTraduccionService.buscarPorTraductor(id)
        }catch(e: Exception) {
            throw IllegalArgumentException("No se encontro el pedido",e)
        }
    }

    @GetMapping("/pedido/tramite/{id}")
    fun buscarPedidoPorTramite(@PathVariable id: Long): List<PedidoTraduccion?>{
        try{
            return pedidoTraduccionService.buscarPorTramite(id)
        }catch(e: Exception) {
            throw IllegalArgumentException("No se encontro el pedido",e)
        }
    }

    @DeleteMapping("/pedido/{id}")
    fun eliminarPedido(@PathVariable id: Long){
        try{
            pedidoTraduccionRepository.deleteById(id)
        }catch(e: Exception) {
            throw IllegalArgumentException("No se encontro el pedido",e)
        }
    }
}