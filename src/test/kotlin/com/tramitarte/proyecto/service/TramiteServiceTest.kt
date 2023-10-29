package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.builder.DocumentacionBuilder
import com.tramitarte.proyecto.builder.TramiteBuilder
import com.tramitarte.proyecto.builder.UsuarioBuilder
import com.tramitarte.proyecto.dominio.*
import com.tramitarte.proyecto.repository.EtapaRepository
import com.tramitarte.proyecto.repository.SolicitudAVORepository
import com.tramitarte.proyecto.repository.TramiteRepository
import com.tramitarte.proyecto.repository.UsuarioRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class TramiteServiceTest {

    @Autowired
    private lateinit var tramiteService: TramiteService

    @Autowired
    private lateinit var tramiteRepository: TramiteRepository

    @Autowired
    private lateinit var solicitudAVOService: SolicitudAVOService

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    lateinit var etapaRepository: EtapaRepository
    @Autowired
    lateinit var solicitudAVORepository: SolicitudAVORepository

    @Test
    fun iniciarTramite_conTramiteIniciado_iniciaTramite() {
        val tramite = tramiteService.iniciarTramite(1)
        assertThat(tramite).isNotNull()
        assertThat(tramite.id).isNotNull()
    }

    @Test
    fun iniciarTramite_conTramiteIniciado_iniciaTramiteConTipo() {
        val tramite = tramiteService.iniciarTramite(1)
        assertThat(tramite.tipo).isNotEmpty()
    }

    @Test
    fun iniciarTramite_conTramiteIniciado_inicitaTramiteConCodigo() {
        val tramite = tramiteService.iniciarTramite(1)
        assertThat(tramite.codigo).isNotEmpty()
    }

    @Test
    fun iniciarTramite_conTramiteAIniciar_iniciaTramiteConRoadmapAsociado() {
        val tramite = tramiteService.iniciarTramite(1)

        assertThat(tramite.etapa).isNotNull
    }

    @Test
    fun eliminar_conTramiteExistente_eliminaTramite() {
        val tramite = tramiteService.iniciarTramite(1)
        val tramitePersistido = tramiteRepository.save(tramite)
        val id: Long = tramitePersistido.id!!
        tramiteService.eliminar(id)
        assertFalse(tramiteRepository.existsById(id))
    }

    @Test
    fun buscarPorUsuario_conUsuarioSinTramite_retornaNull() {
        val usuario: Usuario =
            UsuarioBuilder.conUsuarioInicializado().conNombre("usuario").conApellido("apellido").build()
        val usuarioPersistido: Usuario = usuarioRepository.save(usuario)

        val tramite: Tramite? = tramiteService.buscarPorUsuario(usuarioPersistido)
        assertThat(tramite).isNull()
    }

    @Test
    fun cargarDocumentacionUsuario_conDocumentacionYTramiteIniciado_guardaDocumentacionTramite() {
        var usuario = UsuarioBuilder.conUsuarioInicializado().conNombre("usuario").conApellido("apellido").build()
        var usuarioPersistido = usuarioRepository.save(usuario)
        val etapa2 = Etapa2("CARGAR AVO")
        val etapaPersistida = etapaRepository.save(etapa2)
        val dniFrente = DocumentacionBuilder.conDocumentacionInicializada()
            .conNombre("dni-frente")
            .conArchivoBase64("base64")
            .conTipo("dni-frente")
            .build()
        val dniDorso = DocumentacionBuilder.conDocumentacionInicializada()
            .conNombre("dni-dorso")
            .conArchivoBase64("base64")
            .conTipo("dni-dorso")
            .build()
        val certificadoNacimiento = DocumentacionBuilder.conDocumentacionInicializada()
            .conNombre("certficado-nacimiento")
            .conArchivoBase64("base64")
            .conTipo("certificado-nacimiento")
            .build()
        val documentacionUsuario = mutableListOf(dniFrente, dniDorso, certificadoNacimiento)
        val tramite = TramiteBuilder.conTramiteIniciado()
            .conUsuario(usuarioPersistido)
            .conCodigo("codigo")
            .conTipo("CIUDADANÍA")
            .build()

        tramite.etapa = etapaPersistida

        val tramitePersistido = tramiteRepository.save(tramite)
        val tramiteConDocumentacion = tramiteService.cargaDocumentacionUsuario(tramitePersistido.id!!, documentacionUsuario)
        assertThat(tramiteConDocumentacion.documentacionUsuario).isNotNull
        assertThat(tramiteConDocumentacion.documentacionUsuario).isNotEmpty
    }


//    @Test
//    @Transactional
//    fun avanzar_etapas(){
//        val documento = Documentacion("algo","saasdfsfd")
//        val tramite = tramiteService.iniciarTramite()
//        assertThat(tramite).isNotNull()
//        assertThat(tramite.id).isNotNull()
//
//        //etapa 1
//        assertThrows<NullPointerException> { tramite.avanzarEtapa() }
//
//        assertThat(tramite.etapa.descripcion).isEqualTo("Cargar AVO")
//
//        var solicitudAVO = SolicitudAVO("jorge", "jorgelin", LocalDate.now(), Sexo.MASCULINO)
//
//        solicitudAVOService.guardar(tramite.id!!, solicitudAVO)
//
//        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }
//
//        solicitudAVO = SolicitudAVO("jorge", "jorgelin", LocalDate.now().minusYears(26), Sexo.MASCULINO)
//
//        solicitudAVOService.guardar(tramite.id!!, solicitudAVO)
//
//        //etapa 2
//        tramite.avanzarEtapa()
//
//        assertThat(tramite.etapa.descripcion).isEqualTo("Cargar documentacion de usuario")
//
//        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }
//
//        val documentacionUsuario = DocumentacionUsuario()
//
//        tramiteService.cargaDocumentacionUsuario(tramite.id!!, documentacionUsuario)
//
//        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }
//
//        val documentacionUsuario2 = DocumentacionUsuario().apply {
//            dniFrente = documento
//            dniDorso = documento
//            certificadoNacimiento = documento
//        }
//
//        tramiteService.cargaDocumentacionUsuario(tramite.id!!, documentacionUsuario2)
//
//        //etapa 3
//        tramite.avanzarEtapa()
//
//        assertThat(tramite.etapa.descripcion).isEqualTo("Cargar documentación de los descendientes entre AVO y solicitante")
//
//        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }
//
//        val documentacionAVO = DocumentacionAVO().apply {
//            dniFrente = documento
//            dniDorso = documento
//            certificadoDefunsion = documento
//            certificadoNacimiento = documento
//            certificadoNacimiento = documento
//        }
//
//        val documentacionAVOVacio = DocumentacionAVO()
//
//        val documentacionDescendientes = DocumentacionDescendientes().apply {
//            this.agregarDescendiente(documentacionAVOVacio)
//            this.agregarDescendiente(documentacionAVOVacio)
//        }
//
//        tramiteService.cargaDocumentacionAVO(tramite.id!!, documentacionAVO)
//        tramiteService.cargaDocumentacionDescendientes(tramite.id!!, documentacionDescendientes)
//
//        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }
//
//        val documentacionDescendientesCompleta = DocumentacionDescendientes().apply {
//            this.agregarDescendiente(documentacionAVO)
//            this.agregarDescendiente(documentacionAVO)
//        }
//
//        tramiteService.cargaDocumentacionDescendientes(tramite.id!!, documentacionDescendientesCompleta)
//
//        //etapa 4
//        tramite.avanzarEtapa()
//
//       assertThat(tramite.etapa.descripcion).isEqualTo("Traducir los documentos necesarios")
//
//       assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }
//
//      assertThat(tramite.adjuntosATraducir.size).isEqualTo(7)
//
//       tramiteService.cargarDocumentacionTraducida(tramite.id!!, tramite.adjuntosATraducir)
//
//        //etapa 5
//        tramite.avanzarEtapa()
//
//        assertThat(tramite.etapa.descripcion).isEqualTo("Felicidades, ya tiene todo lo necesario para presentarse al " +
//                "consuldado y pedir su ciudadania")
//    }


}