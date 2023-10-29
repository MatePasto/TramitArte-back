package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.dominio.Documentacion
import com.tramitarte.proyecto.dominio.SolicitudDescarga
import com.tramitarte.proyecto.repository.SolicitudDescargaRepository
import com.tramitarte.proyecto.service.SolicitudDescargaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
class SolicitudDescargaController {

    @Autowired
    lateinit var solicitudDescargaRepository: SolicitudDescargaRepository
    @Autowired
    lateinit var  solicitudDescargaService: SolicitudDescargaService

    @PostMapping("/solicitud-descarga/solicitante/{idSolicitante}/traductor/{idTraductor}")
    fun crearSolicitudDescarga(@PathVariable idSolicitante: Long, @PathVariable idTraductor: Long, @RequestBody documentos: MutableList<Documentacion>){
        try{
            solicitudDescargaService.crearSolicitudDescarga(idSolicitante, idTraductor, documentos)
        }catch(e: Exception) {
            throw IllegalArgumentException("No se pudo crear la solicitud de descarga",e)
        }
    }

    @GetMapping("/solicitud-descarga/solicitante/{idSolicitante}")
    fun buscarSolicitudDescargaPorSolicitante(@PathVariable idSolicitante: Long): List<SolicitudDescarga?>{
        try{
            return solicitudDescargaService.buscarSolicitudDescargaPorSolicitante(idSolicitante)
        }catch(e: Exception) {
            throw IllegalArgumentException("No se encontraron solicitudes de descarga",e)
        }
    }

    @DeleteMapping("/solicitud-descarga/{id}")
    fun eliminarSolicitudDescarga(@PathVariable id: Long){
        try{
            solicitudDescargaRepository.deleteById(id)
        }catch(e: Exception) {
            throw IllegalArgumentException("error al eliminar la solicitud de descarga",e)
        }
    }
}