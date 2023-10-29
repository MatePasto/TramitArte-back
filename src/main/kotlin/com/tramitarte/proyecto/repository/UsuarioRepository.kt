package com.tramitarte.proyecto.repository

import com.tramitarte.proyecto.dominio.Rol
import com.tramitarte.proyecto.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Long> {
    fun findByCorreoElectronico(correoElectonico: String): Usuario

    fun findByNombreAndAndApellidoAndPrecio(nombre: Optional<String>, apellido: Optional<String>, precio: Optional<Float>): Usuario

    fun findByRol(rol: Rol): List<Usuario>

    fun findByRolAndCorreoElectronicoContaining(rol: Rol, correoElectonico: String): List<Usuario>

}