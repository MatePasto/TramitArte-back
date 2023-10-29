package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.Documentacion
import com.tramitarte.proyecto.repository.DocumentacionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.print.Doc

@Service
class DocumentacionService {

    @Autowired
    lateinit var documentacionRepository: DocumentacionRepository

    fun guardar(documentacion: Documentacion): Documentacion {
        return documentacionRepository.save(documentacion)
    }

    @Transactional
    fun modificar(id: Long, doc: Documentacion) {
        var documentacion = documentacionRepository.findById(id).get()
        documentacion.tipo = doc.tipo
        documentacion.nombre = doc.nombre
        documentacion.archivoBase64 = doc.archivoBase64
        documentacionRepository.save(documentacion)
    }
}