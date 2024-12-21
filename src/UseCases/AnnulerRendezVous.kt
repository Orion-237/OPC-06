package usecases

import repositories.RendezVousRepository

class AnnulerRendezVous(private val rendezVousRepository: RendezVousRepository) {
    suspend fun execute(nomMedecin: String, nomPatient: String, jour: String, heure: String): Boolean {
        return rendezVousRepository.delete(nomMedecin, nomPatient, jour, heure)
    }
}
