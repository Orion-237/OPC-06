package usecases

import entities.Disponibilite
import entities.Medecin
import entities.Patient
import entities.RendezVous
import repositories.MedecinRepository
import repositories.RendezVousRepository

class ReserverRendezVous(
    private val medecinRepository: MedecinRepository,
    private val rendezVousRepository: RendezVousRepository
) {
    suspend fun execute(nomMedecin: String, nomPatient: String, jour: String, heure: String): Boolean {
        val medecin = medecinRepository.findByName(nomMedecin) ?: return false
        val disponibilite = medecin.disponibilites.find { it.jour == jour && it.heure == heure } ?: return false

        val patient = Patient(nomPatient)
        val rendezVous = RendezVous(medecin, patient, disponibilite)
        rendezVousRepository.save(rendezVous)

        medecin.disponibilites.remove(disponibilite)
        medecinRepository.save(medecin)
        return true
    }
}
