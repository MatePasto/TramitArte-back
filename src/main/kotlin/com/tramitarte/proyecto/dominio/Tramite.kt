package com.tramitarte.proyecto.dominio

import jakarta.persistence.*

@Entity
class Tramite(codigo: String, tipo: String, etapa: Etapa) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var codigo: String = codigo
    var tipo: String = tipo
    @OneToOne
    var etapa = etapa
    @OneToOne
    var usuario: Usuario? = null
    @OneToMany
    var adjuntosATraducir = mutableListOf<Documentacion>()
    @OneToMany(cascade = [CascadeType.ALL])
    var documentacionUsuario: List<Documentacion>? = null
    @OneToMany(cascade = [CascadeType.ALL])
    var documentacionAVO: List<Documentacion>? = null
    @OneToMany(cascade = [CascadeType.ALL])
    var documentacionDescendientes: List<Documentacion>? = null
    @OneToMany(cascade = [CascadeType.ALL])
    var documentacionTraducida: List<Documentacion>? = null
    @ManyToOne
    var solicitudAvo: SolicitudAVO? = null
    var cantidadDescendientes: Long = 0
    fun cargarAvo(avo: SolicitudAVO) {
        solicitudAvo = avo
    }

    fun tieneDocumentacionTraducirda(): Boolean = documentacionTraducida!!.size == adjuntosATraducir.size

    fun avanzarEtapa() {
        etapa.verificarEtapa(this)
    }

    fun agregarAdjuntosATraducir(adjuntos: List<Documentacion>) {
        this.adjuntosATraducir.addAll(adjuntos)
    }
}