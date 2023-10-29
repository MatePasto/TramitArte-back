package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.SolicitudAVO
import com.tramitarte.proyecto.dominio.Usuario
import com.tramitarte.proyecto.repository.SolicitudAVORepository
import com.tramitarte.proyecto.repository.TramiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert

@Service
class SolicitudAVOService {
    @Autowired
    lateinit var solicitudAVORepository: SolicitudAVORepository

    @Autowired
    lateinit var tramiteRepository: TramiteRepository
    @Autowired
    lateinit var tramiteService: TramiteService

    @Transactional
    fun guardar(id: Long, solicitudAVO: SolicitudAVO): SolicitudAVO {
        validarNombreYApellido(solicitudAVO)
        val tramite = tramiteRepository.findById(id).get()
        tramite.cargarAvo(solicitudAVO)
        solicitudAVORepository.save(solicitudAVO)
        tramiteRepository.save(tramite)
        tramiteService.avanzarEtapa(id)
        return solicitudAVO
    }

    private fun validarNombreYApellido(solicitudAVO: SolicitudAVO) {
        Assert.hasText(solicitudAVO.nombre, "El nombre del AVO es obligatorio.")
        Assert.hasText(solicitudAVO.apellido, "El apellido del AVO es obligatorio.")
    }

    @Transactional
    fun modificar(solicitudAVO: SolicitudAVO): SolicitudAVO {
        validarSiExisteSolicitud(solicitudAVO)
        validarNombreYApellido(solicitudAVO)
        return solicitudAVORepository.save(solicitudAVO)
    }

    private fun validarSiExisteSolicitud(solicitudAVO: SolicitudAVO) {
        val solicitudPersistida = solicitudAVO.id?.let { solicitudAVORepository.findByIdOrNull(solicitudAVO.id) }
        Assert.isTrue(solicitudPersistida !== null, "La solicitud a modificar no existe.")
    }

    fun buscarAVOPorUsuario(usuario: Usuario): SolicitudAVO? {
        val tramiteABuscar = tramiteService.buscarPorUsuario(usuario)
        return tramiteABuscar?.solicitudAvo

    }
}