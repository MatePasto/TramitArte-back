package com.tramitarte.proyecto.dominio

data class NotificacionDTO (
        val idNotificacion: Long,
        val idUsuarioOrigen: Long?,
        val idUsuarioDestino: Long?,
        val descripcion: String
)