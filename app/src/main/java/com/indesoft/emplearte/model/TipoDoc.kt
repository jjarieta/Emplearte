package  com.indesoft.emplearte.model

class TipoDoc {
    var CodigoDocumento : Int? = null
    var TipoDocumento : String? = null

    constructor(CodigoDocumento: Int?, TipoDocumento: String?) {
        this.CodigoDocumento = CodigoDocumento
        this.TipoDocumento = TipoDocumento
    }
    override fun toString(): String {
        return "$TipoDocumento"
    }
}