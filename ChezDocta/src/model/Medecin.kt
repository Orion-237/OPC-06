package model


data class Medecin(
    val nom:String,
    val pwd:String,
    val disponibillite: Disponibillite? = null
)