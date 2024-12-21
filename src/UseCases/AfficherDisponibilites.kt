package usecases

import entities.Disponibilite
import repositories.MedecinRepository

class AfficherDisponibilites(private val medecinRepository: MedecinRepository) {
    fun execute(nomMedecin: String): List<Disponibilite> {
        val medecin = medecinRepository.findByName(nomMedecin)
        return medecin?.disponibilites ?: emptyList()
    }
}
