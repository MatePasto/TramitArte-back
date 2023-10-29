package com.tramitarte.proyecto.documentacion

//@Entity
//abstract class Documento{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    var id: Long = 0
//    @OneToOne
//    open var certificadoNacimiento: Documentacion? = null
//
//
//    abstract fun validar(): Boolean
//}

//@Entity
//class DocumentacionAVO(certificadoNacimiento: Documentacion, certificadoMatrimonio : Documentacion, certificadoDefuncion: Documentacion) {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    var id: Long = 0
//    @OneToOne
//    var certificadoNacimiento = certificadoNacimiento
//
//    @OneToOne
//    var certificadoMatrimonio = certificadoMatrimonio
//
//    @OneToOne
//    var certificadoDefuncion = certificadoDefuncion
//
//    fun validar(): Boolean = certificadoNacimiento != null
//}
//
//@Entity
//class DocumentacionDescendientes {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    var id: Long = 0
//    @OneToMany
//    var descendientes: MutableList<DocumentacionAVO> = mutableListOf()
//    fun validar(): Boolean = descendientes.all { it.validar() }
//
//    fun agregarDescendiente(descendiente: DocumentacionAVO) {
//        descendientes.add(descendiente)
//    }
//}
//
//@Embeddable
//class DocumentacionUsuario(dniFrente: Documentacion, dniDorso: Documentacion, certificadoNacimiento: Documentacion) {
//    @OneToOne
//    var certificadoNacimiento = certificadoNacimiento
//
//    @OneToOne
//    var dniFrente = dniFrente
//
//    @OneToOne
//    var dniDorso = dniDorso
//    fun validar(): Boolean = dniFrente != null && dniDorso != null && certificadoNacimiento != null
//}