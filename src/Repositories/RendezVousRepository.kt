package repositories

import entities.RendezVous

interface RendezVousRepository {
    fun save(rendezVous: RendezVous)
    fun findByMedecinAndDisponibilite(nomMedecin: String, jour: String, heure: String): RendezVous?
    fun findAll(): List<RendezVous>
    fun delete(nomMedecin: String, nomPatient: String, jour: String, heure: String): Boolean
}
