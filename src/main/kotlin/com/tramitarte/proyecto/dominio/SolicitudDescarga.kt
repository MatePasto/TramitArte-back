package com.tramitarte.proyecto.dominio

import jakarta.persistence.*

@Entity
class SolicitudDescarga(solicitante: Usuario?, traductor: Usuario?) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "traductor_id")
    var traductor: Usuario? = traductor
    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    var solicitante: Usuario? = solicitante
    @OneToMany(cascade = [CascadeType.ALL])
    var documentacion: List<Documentacion>? = null
}