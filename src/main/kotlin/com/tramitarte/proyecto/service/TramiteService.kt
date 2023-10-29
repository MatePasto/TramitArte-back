package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.*
import com.tramitarte.proyecto.repository.EtapaRepository
import com.tramitarte.proyecto.repository.TramiteRepository
import com.tramitarte.proyecto.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID.randomUUID

@Service
class TramiteService {

    @Autowired
    lateinit var tramiteRepository: TramiteRepository

    @Autowired
    lateinit var etapaRepository: EtapaRepository

    @Autowired
    lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Transactional
    fun iniciarTramite(idUsuario: Long): Tramite {
        val usuario: Usuario? = usuarioService.buscarPorId(idUsuario)
        val tramite = Tramite(codigo = randomUUID().toString(), tipo = "CIUDADANÍA", etapa = Etapa1("Cargar AVO"))
        tramite.usuario = usuario
        etapaRepository.save(tramite.etapa)
        return tramiteRepository.save(tramite)
    }

    @Transactional
    fun cargaDocumentacionUsuario(id: Long, documentacionUsuario: List<Documentacion>): Tramite {
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionUsuario = documentacionUsuario
        val documento: MutableList<Documentacion> = mutableListOf(documentacionUsuario[2]) //se guarda solo el certificado de nacimiento del usuario y no los dni
        tramite.agregarAdjuntosATraducir(documento)
        tramite.avanzarEtapa()
        etapaRepository.save(tramite.etapa)
        val tramitePersistido = tramiteRepository.save(tramite)
        return tramitePersistido
    }

    @Transactional
    fun cargaDocumentacionAVO(id: Long, documentacionAVO: MutableList<Documentacion>): Tramite {
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionAVO = documentacionAVO
        tramite.agregarAdjuntosATraducir(documentacionAVO)
        tramite.avanzarEtapa()
        etapaRepository.save(tramite.etapa)
        val tramitePersistido = tramiteRepository.save(tramite)
        return tramitePersistido
    }

    @Transactional
    fun cargaDocumentacionDescendientes(
        id: Long,
        documentacionDescendientes: MutableList<Documentacion>
    ): Tramite {
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionDescendientes = documentacionDescendientes
        tramite.agregarAdjuntosATraducir(documentacionDescendientes)
        tramite.avanzarEtapa()
        etapaRepository.save(tramite.etapa)
        val tramitePersistido = tramiteRepository.save(tramite)
        return tramitePersistido
    }

    @Transactional
    fun cargarDocumentacionTraducida(
            id: Long,
            documentosTraducidos: MutableList<Documentacion>
    ): Tramite {
        val usuario = usuarioRepository.findById(id).get()
        val tramite = tramiteRepository.findByUsuario(usuario)
        tramite!!.documentacionTraducida = documentosTraducidos
        tramite.avanzarEtapa()
        etapaRepository.save(tramite.etapa)
        val tramitePersistido = tramiteRepository.save(tramite)
        return tramitePersistido
    }

    @Transactional
    fun avanzarEtapa(id: Long): Etapa {
        val tramite = tramiteRepository.findById(id).get()
        tramite.avanzarEtapa()
        etapaRepository.save(tramite.etapa)
        tramiteRepository.save(tramite)
        return tramite.etapa
    }

    fun mostrarDocumentacion( id: Long): ResponseEntity<DocumentListDTO> {
        val tramite= tramiteRepository.findById(id).get()
        var lista = listOf(tramite.documentacionAVO,tramite.documentacionUsuario,tramite.documentacionDescendientes,tramite.documentacionTraducida).flatMap { it!! }
        return ResponseEntity(DocumentListDTO(lista), HttpStatus.OK)
    }

    fun eliminar(id: Long) {
        validarTramiteExistente(id)
        tramiteRepository.deleteById(id)
    }

    fun buscarPorUsuario(usuario: Usuario?): Tramite? {
        return tramiteRepository.findByUsuario(usuario)
    }

    private fun validarTramiteExistente(id: Long) {
        val existeTramite = tramiteRepository.existsById(id)
        if (!existeTramite) {
            throw IllegalArgumentException("El trámite a eliminar no existe")
        }
    }
}