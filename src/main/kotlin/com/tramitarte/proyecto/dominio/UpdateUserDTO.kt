package com.tramitarte.proyecto.dominio

import java.time.LocalDate

data class UpdateUserDTO(
        var username: String,
        var apellido:String,
        var nombre:String,

)