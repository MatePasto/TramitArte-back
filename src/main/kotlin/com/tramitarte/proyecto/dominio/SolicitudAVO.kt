package com.tramitarte.proyecto.dominio

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
class SolicitudAVO(
    nombre: String,
    apellido: String,
    fechaNacimiento: LocalDate,
    sexo: Sexo
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var nombre: String = nombre
    var apellido: String = apellido
    @JsonFormat(pattern = "dd/MM/yyyy")
    var fechaNacimiento: LocalDate = fechaNacimiento
    var sexo: Sexo = sexo

    fun validar(): Boolean = nombre.isNotBlank() && apellido.isNotBlank() && fechaNacimiento.isBefore(LocalDate.now())
}