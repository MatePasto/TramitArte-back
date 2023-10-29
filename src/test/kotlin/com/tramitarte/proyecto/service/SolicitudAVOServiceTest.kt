//package com.tramitarte.proyecto.service
//
//import com.tramitarte.proyecto.builder.SolicitudAVOBuilder
//import com.tramitarte.proyecto.dominio.Sexo
//import com.tramitarte.proyecto.dominio.SolicitudAVO
//import com.tramitarte.proyecto.repository.SolicitudAVORepository
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.assertj.core.api.Assertions.*
//import java.time.LocalDate
//
//@SpringBootTest
//class SolicitudAVOServiceTest {
//    @Autowired
//    lateinit var solicitudAVOService: SolicitudAVOService
//    @Autowired
//    lateinit var tramiteService: TramiteService
//
//    @Autowired
//    lateinit var solicitudAVORepository: SolicitudAVORepository
//    @Test
//    fun guardar_conAVOaGuardar_retornaAVO() {
//        val tramite = tramiteService.iniciarTramite()
//        assertThat(tramite).isNotNull()
//        assertThat(tramite.id).isNotNull()
//        val solicitudRecibida = SolicitudAVOBuilder
//            .conSolicitudInicializada().conNombre("Nombre AVO").conApellido("Apellido AVO")
//            .build()
//
//        val solicitudAVO: SolicitudAVO = solicitudAVOService.guardar(tramite.id!!, solicitudRecibida)
//
//        assertThat(solicitudAVO).isNotNull()
//        assertThat(solicitudAVO.id).isNotNull()
//    }
//
//    @Test
//    fun guardar_conAVOAGuardarConNombre_retornaSolicitudAVOConNombre() {
//        val tramite = tramiteService.iniciarTramite()
//        assertThat(tramite).isNotNull()
//        assertThat(tramite.id).isNotNull()
//        val solicitudRecibida = SolicitudAVOBuilder.conSolicitudInicializada()
//            .conNombre("Nombre AVO").conApellido("Apellido AVO")
//            .build()
//
//        val solicitudPersistida = solicitudAVOService.guardar(tramite.id!!, solicitudRecibida)
//
//        assertThat(solicitudPersistida.nombre).isNotEmpty()
//    }
//
//    @Test
//    fun guardar_conAVOAGuardarConApellido_retornaSolicitudAVOConApellido() {
//        val tramite = tramiteService.iniciarTramite()
//        assertThat(tramite).isNotNull()
//        assertThat(tramite.id).isNotNull()
//        val solicitudRecibida = SolicitudAVOBuilder.conSolicitudInicializada()
//            .conNombre("Nombre AVO")
//            .conApellido("Apellido AVO")
//            .build()
//
//        val solicitudPersistida = solicitudAVOService.guardar(tramite.id!!, solicitudRecibida)
//
//        assertThat(solicitudPersistida.apellido).isNotEmpty()
//    }
//
//    @Test
//    fun guardar_conAVOAGuardarConFechaNacimiento_retornaSolicitudAVOConFechaNacimiento() {
//        val tramite = tramiteService.iniciarTramite()
//        assertThat(tramite).isNotNull()
//        assertThat(tramite.id).isNotNull()
//        val solicitudRecibida = SolicitudAVOBuilder.conSolicitudInicializada()
//            .conNombre("NombreAVO")
//            .conApellido("ApellidoAVO")
//            .build()
//
//        val solicitudPersistida = solicitudAVOService.guardar(tramite.id!!, solicitudRecibida)
//
//        assertThat(solicitudPersistida.fechaNacimiento).isNotNull()
//        assertThat(solicitudPersistida.fechaNacimiento).isAfter(LocalDate.now().minusYears(200))
//    }
//
//    @Test
//    fun guardar_conAVOAGuardarConSexo_retornaSolicitudAVOConSexo() {
//        val tramite = tramiteService.iniciarTramite()
//        assertThat(tramite).isNotNull()
//        assertThat(tramite.id).isNotNull()
//        val solicitudRecibida = SolicitudAVOBuilder.conSolicitudInicializada()
//            .conNombre("NombreAVO")
//            .conApellido("ApellidoAVO")
//            .conSexo(Sexo.MASCULINO)
//            .build()
//
//        val solicitudPersistida = solicitudAVOService.guardar(tramite.id!!, solicitudRecibida)
//
//        assertThat(solicitudPersistida.sexo).isEqualTo(Sexo.MASCULINO)
//    }
//
//    @Test
//    fun guardar_conNombreVacio_lanzaExcepcion() {
//        val tramite = tramiteService.iniciarTramite()
//        assertThat(tramite).isNotNull()
//        assertThat(tramite.id).isNotNull()
//        val solicitudRecibida = SolicitudAVOBuilder.conSolicitudInicializada()
//            .conNombre("")
//            .conApellido("Apellido")
//            .conSexo(Sexo.FEMENINO)
//            .build()
//
//        assertThatIllegalArgumentException()
//            .isThrownBy{ solicitudAVOService.guardar(tramite.id!!, solicitudRecibida) }
//            .withMessage("El nombre del AVO es obligatorio.")
//    }
//
//    @Test
//    fun guardar_conApellidoVacio_lanzaExcepcion() {
//        val tramite = tramiteService.iniciarTramite()
//        assertThat(tramite).isNotNull()
//        assertThat(tramite.id).isNotNull()
//        val solicitudRecibida = SolicitudAVOBuilder.conSolicitudInicializada()
//            .conNombre("Nombre")
//            .conApellido("")
//            .conSexo(Sexo.MASCULINO)
//            .build()
//
//        assertThatIllegalArgumentException()
//            .isThrownBy{ solicitudAVOService.guardar(tramite.id!!, solicitudRecibida) }
//            .withMessage("El apellido del AVO es obligatorio.")
//    }
//
//    @Test
//    fun modificar_conSolicitudAVOGuardada_modificaNombre() {
//        val solicitudExistente = SolicitudAVOBuilder.conSolicitudInicializada()
//            .conNombre("Nombre Avo")
//            .conApellido("Apellido AVO")
//            .build()
//        val solicitudExistentePersistida = solicitudAVORepository.save(solicitudExistente)
//
//        val nombreNuevo = "Nuevo nombre"
//        solicitudExistentePersistida.nombre = nombreNuevo
//        val solicitudPersistida = solicitudAVOService.modificar(solicitudExistentePersistida)
//
//        assertThat(solicitudPersistida.nombre).isEqualTo(nombreNuevo)
//    }
//
//    @Test
//    fun modificar_conSolicitudAVOInexistente_lanzaExcepcion() {
//        val solicitudAVOInexistente = SolicitudAVOBuilder.conSolicitudInicializada()
//            .conNombre("NombreAVO")
//            .conApellido("ApellidoAVO")
//            .conSexo(Sexo.FEMENINO)
//            .build()
//
//        assertThatIllegalArgumentException()
//            .isThrownBy{ solicitudAVOService.modificar(solicitudAVOInexistente) }
//            .withMessage("La solicitud a modificar no existe.")
//    }
//
//    @Test
//    fun modificar_conNombreVacio_lanzaExcepcion() {
//        val solicitudExistente = SolicitudAVOBuilder.conSolicitudInicializada()
//            .conNombre("Nombre Avo")
//            .conApellido("Apellido AVO")
//            .build()
//        val solicitudExistentePersistida = solicitudAVORepository.save(solicitudExistente)
//        solicitudExistentePersistida.nombre = ""
//
//        assertThatIllegalArgumentException()
//            .isThrownBy{ solicitudAVOService.modificar(solicitudExistentePersistida) }
//            .withMessage("El nombre del AVO es obligatorio.")
//    }
//
//    @Test
//    fun modificar_conApellidoVacio_lanzaExcepcion() {
//        val solicitudExistente = SolicitudAVOBuilder.conSolicitudInicializada()
//            .conNombre("Nombre Avo")
//            .conApellido("Apellido AVO")
//            .build()
//        val solicitudExistentePersistida = solicitudAVORepository.save(solicitudExistente)
//        solicitudExistentePersistida.apellido = ""
//
//        assertThatIllegalArgumentException()
//            .isThrownBy{ solicitudAVOService.modificar(solicitudExistentePersistida) }
//            .withMessage("El apellido del AVO es obligatorio.")
//    }
//}
