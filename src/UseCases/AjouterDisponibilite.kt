package usecases

import entities.Disponibilite
import entities.Medecin
import repositories.MedecinRepository

class AjouterDisponibilite(private val medecinRepository: MedecinRepository) {
    suspend fun execute(nomMedecin: String, jour: String, heure: String): Boolean {
        val medecin = medecinRepository.findByName(nomMedecin) ?: return false
        val disponibilite = Disponibilite(jour, heure)
        medecin.disponibilites.add(disponibilite)
        medecinRepository.save(medecin)
        return true
    }
}
