package com.tramitarte.proyecto.dominio

import jakarta.persistence.*

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
abstract class Notificacion() {
    constructor(
        _usuarioOrigen: Usuario,
        _usuarioDestino: Usuario
    ) : this() {
        usuarioOrigen = _usuarioOrigen
        usuarioDestino = _usuarioDestino
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0

    @ManyToOne
    @JoinColumn(name = "usuario_origen_id")
    open lateinit var usuarioOrigen: Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_destino_id")
    open lateinit var usuarioDestino: Usuario
    open lateinit var descripcion: String
    open lateinit var tipo: String
}

@Entity
class Alerta(): Notificacion() {
    constructor(
        _usuarioOrigen: Usuario,
        _usuarioDestino: Usuario,
        _descripcion: String
    ) : this() {
        usuarioOrigen = _usuarioOrigen
        usuarioDestino = _usuarioDestino
        descripcion = _descripcion
        tipo = "Alerta"
    }

    var alertaVisualizada: Boolean = false
}
