package interfaceadapters

import entities.Disponibilite
import entities.Medecin
import entities.Patient
import entities.RendezVous
import repositories.RendezVousRepository
import java.io.File

class FichierRendezVousRepository(private val fichier: String) : RendezVousRepository {
    override fun save(rendezVous: RendezVous) {
        val ligne = "${rendezVous.medecin.nom},${rendezVous.patient.nom},${rendezVous.disponibilite.jour},${rendezVous.disponibilite.heure}"
        File(fichier).appendText("$ligne\n")
    }

    override fun findByMedecinAndDisponibilite(nomMedecin: String, jour: String, heure: String): RendezVous? {
        return File(fichier).readLines().map { ligne ->
            val parts = ligne.split(",")
            RendezVous(
                Medecin(parts[0]),
                Patient(parts[1]),
                Disponibilite(parts[2], parts[3])
            )
        }.find { it.medecin.nom == nomMedecin && it.disponibilite.jour == jour && it.disponibilite.heure == heure }
    }

    override fun findAll(): List<RendezVous> {
        return File(fichier).readLines().map { ligne ->
            val parts = ligne.split(",")
            RendezVous(
                Medecin(parts[0]),
                Patient(parts[1]),
                Disponibilite(parts[2], parts[3])
            )
        }
    }

    override fun delete(nomMedecin: String, nomPatient: String, jour: String, heure: String): Boolean {
        val lignes = File(fichier).readLines()
        val nouvellesLignes = lignes.filterNot { ligne ->
            val parts = ligne.split(",")
            parts[0] == nomMedecin && parts[1] == nomPatient && parts[2] == jour && parts[3] == heure
        }
        if (lignes.size == nouvellesLignes.size) return false

        File(fichier).writeText(nouvellesLignes.joinToString("\n"))
        return true
    }
}
