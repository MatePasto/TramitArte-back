package com.tramitarte.proyecto.builder

import com.tramitarte.proyecto.dominio.Sexo
import com.tramitarte.proyecto.dominio.SolicitudAVO
import java.time.LocalDate

class SolicitudAVOBuilder {
    private val fechaNacimientoAVOValida = LocalDate.now().minusDays(200)
    private val solicitudAVO = SolicitudAVO(nombre = "", apellido = "", sexo = Sexo.FEMENINO, fechaNacimiento = fechaNacimientoAVOValida)

    companion object {
        fun conSolicitudInicializada(): SolicitudAVOBuilder = SolicitudAVOBuilder()
    }

    fun conId(id: Long?): SolicitudAVOBuilder {
        solicitudAVO.id = id
        return this
    }

    fun conNombre(nombre: String): SolicitudAVOBuilder {
        solicitudAVO.nombre = nombre
        return this
    }

    fun conApellido(apellido: String): SolicitudAVOBuilder {
        solicitudAVO.apellido = apellido
        return this
    }

    fun conSexo(sexo: Sexo): SolicitudAVOBuilder {
        solicitudAVO.sexo = sexo
        return this
    }

    fun build() = solicitudAVO
}