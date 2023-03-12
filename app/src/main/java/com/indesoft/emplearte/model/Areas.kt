package com.indesoft.emplearte.model

class Areas(val  Areas: List<Areas>) {

    var IdArea :Int  ? = null
    var NombreArea: String?  = null



}

class AreasC  {

    var IdArea :Int  ? = null
    var NombreArea: String?  = null

    constructor(IdArea: Int?, NombreArea: String?) {
        this.IdArea = IdArea
        this.NombreArea = NombreArea
    }



    override fun toString(): String {
        return "$NombreArea"
    }
}

