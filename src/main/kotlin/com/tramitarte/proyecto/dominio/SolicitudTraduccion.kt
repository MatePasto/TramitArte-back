package com.tramitarte.proyecto.dominio

import jakarta.persistence.*


@Entity
class SolicitudTraduccion(solicitante: Usuario, traductor: Usuario) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    var solicitante: Usuario = solicitante
    @ManyToOne
    @JoinColumn(name = "traductor_id")
    var traductor: Usuario = traductor
}