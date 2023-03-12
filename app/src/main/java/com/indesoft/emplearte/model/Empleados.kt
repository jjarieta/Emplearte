package com.indesoft.emplearte.model

class Empleados {
    var TipoDocumento : String? = null
    var Documento     : Int?    = null

    constructor(
        TipoDocumento: String?,
        Documento: Int?,
        Nombres: String?,
        Apellidos: String?,
        Direccion: String?,
        Telefono: String?
    ) {
        this.TipoDocumento = TipoDocumento
        this.Documento = Documento
        this.Nombres = Nombres
        this.Apellidos = Apellidos
        this.Direccion = Direccion
        this.Telefono = Telefono
    }

    constructor(TipoDocumento: String?, Documento: Int?) {
        this.TipoDocumento = TipoDocumento
        this.Documento = Documento
    }

    var Nombres       : String? = null
    var Apellidos     : String? = null

    var Direccion    : String? = null
    var Telefono : String? = null
}