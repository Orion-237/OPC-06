package entities

import entities.Disponibilite

data class Medecin(val nom: String, val disponibilites: MutableList<Disponibilite> = mutableListOf())
