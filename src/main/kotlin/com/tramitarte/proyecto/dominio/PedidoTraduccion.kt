package com.tramitarte.proyecto.dominio

import jakarta.persistence.*

@Entity
class PedidoTraduccion(tramite: Tramite?, traductor: Usuario?) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "tramite_pedido_id")
    var tramite: Tramite? = tramite

    @ManyToOne
    @JoinColumn(name = "traductor_pedido_id")
    var traductor: Usuario? = traductor
}