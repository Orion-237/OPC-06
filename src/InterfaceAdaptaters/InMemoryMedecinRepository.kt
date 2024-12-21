package interfaceadapters

import entities.Medecin
import repositories.MedecinRepository
import java.io.File

class InMemoryMedecinRepository(private val fichier: String) : MedecinRepository {
    private val medecins = mutableListOf<Medecin>()

    override fun save(medecin: Medecin) {
        val medecins = findAll().toMutableList()

        // Supprimer l'ancien médecin (s'il existe)
        medecins.removeIf { it.nom == medecin.nom }

        // Ajouter le médecin mis à jour
        medecins.add(medecin)

        // Réécrire tout le fichier
        File(fichier).writeText(medecins.joinToString("\n") { med ->
            val disponibilites = med.disponibilites.joinToString(";") { "${it.jour}:${it.heure}" }
            "${med.nom}|$disponibilites"
        })
    }

    override fun findByName(nom: String): Medecin? {
        return medecins.find { it.nom == nom }
    }

    override fun findAll(): List<Medecin> {
        return medecins
    }
}
