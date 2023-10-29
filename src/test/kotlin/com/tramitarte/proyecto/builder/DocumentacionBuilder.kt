package com.tramitarte.proyecto.builder

import com.tramitarte.proyecto.dominio.Documentacion

class DocumentacionBuilder {
    private val documentacionBase = Documentacion(nombre = "", archivoBase64 = "", tipo = "")
    companion object {
        fun conDocumentacionInicializada(): DocumentacionBuilder = DocumentacionBuilder()
    }

    fun conId(id: Long?): DocumentacionBuilder {
        documentacionBase.id = id
        return this
    }

    fun conNombre(nombre: String): DocumentacionBuilder {
        documentacionBase.nombre = nombre
        return this
    }

    fun conArchivoBase64(archivoBase64: String): DocumentacionBuilder {
        documentacionBase.archivoBase64 = archivoBase64
        return this
    }

    fun conTipo(tipo: String): DocumentacionBuilder {
        documentacionBase.tipo = tipo
        return this
    }

    fun build() = documentacionBase
}