package  com.indesoft.emplearte.model

class SubAreas {
    var IdSubArea :Int  ? = null
    var IdArea :Int  ? = null
    var NombreSubArea: String?  = null

    constructor(IdSubArea: Int?, IdArea: Int?, NombreSubArea: String?) {
        this.IdSubArea = IdSubArea
        this.IdArea = IdArea
        this.NombreSubArea = NombreSubArea
    }
}

class SubAreasC constructor( val  IdSubAreaC: Int, private  val  IdAreaC: Int, val NombreSubAreaC:String) {

    var IdSubArea :Int  ? = null
    var IdArea :Int  ? = null
    var NombreSubArea: String?  = null

    override fun toString(): String {
        return "$NombreSubAreaC"
    }
}