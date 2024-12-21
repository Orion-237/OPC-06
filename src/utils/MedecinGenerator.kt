package utils

import entities.Medecin
import kotlin.random.Random

class MedecinGenerator {
    private val noms = listOf(
        "Pr Liam Ndjock", "Dr Francis Talla ", "Pr Mathieu Ntono",
        "Dr Weber", "Dr Alex", "Dr Synthia", "Dr Nelson",
        "Dr Jean Pierre Atangana", "Dr Anderson", "Dr Sibelle"
    )
    private val specialites = listOf(
        "Cardiologie", "Neurologie", "Pédiatrie", "Gynécologie",
        "Dermatologie", "Orthopédie", "Oncologie",
        "Psychiatrie", "Ophtalmologie", "Anesthésiologie"
    )

    fun creerListeMedecins(nb: Int): List<Medecin> {
        if (nb > noms.size || nb > specialites.size) {
            throw IllegalArgumentException("❌ Le nombre de médecins demandé dépasse la taille des listes disponibles.")
        }
        return (0 until nb).map { i ->
            Medecin(i + 1, noms[i], specialites[Random.nextInt(specialites.size)])
        }
    }
}