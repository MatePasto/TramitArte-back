package com.tramitarte.proyecto.dominio

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Usuario(username: String, nombre: String, apellido: String, rol: Rol, precio: Float, correoElectronico: String, fechaDeNacimiento: LocalDate, fotoPerfil: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var username: String = username
    var nombre: String = nombre
    var apellido: String = apellido
    var rol: Rol = rol
    var precio: Float = precio
    var correoElectronico: String = correoElectronico
    var fechaDeNacimiento: LocalDate = fechaDeNacimiento
    var nesecitaTraduccion: Boolean = false
    var fotoPerfil: String = fotoPerfil

    fun updateUser(update: UpdateUserDTO){
          username = update.username
          apellido = update.apellido
          nombre = update.nombre
    }
}