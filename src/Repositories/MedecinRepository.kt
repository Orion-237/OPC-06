package repositories

import entities.Medecin

interface MedecinRepository {
    fun save(medecin: Medecin)
    fun findByName(nom: String): Medecin?
    fun findAll(): List<Medecin>
}
