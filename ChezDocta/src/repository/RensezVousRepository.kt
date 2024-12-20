package repository

import model.RendezVous
import java.io.File
import java.time.LocalDate
import java.time.LocalTime

val filenamerendezvous = "$directoryPath/rendezvous.txt"


interface RensezVousRepository {
    fun addRendezvous(patient: String, index : Int, creneaux : List<Creneau>) : Boolean
    fun getRendezVous() : List<RendezVous>
    fun deleteRendezVous(index: Int,creneaux: List<Creneau>) : Boolean

}

class RendezVousRepositoryImpl : RensezVousRepository {

    override fun addRendezvous(patient: String, index: Int, creneaux: List<Creneau>): Boolean {

        var creneau = creneaux.toMutableList()
        if (index-1 < 0 || index-1 >= creneau.size) {
            return false
        }

        val updateCreneau = creneau[index - 1]

        val nbr = updateCreneau.place
        if (nbr > 0) {
            val file = File(filenamecrenaux)
            if (updateCreneau.place > 0) {
                val updatedCreneaux = creneau.map { creneau ->
                    if (creneau == updateCreneau) {
                        creneau.copy(place = creneau.place - 1)
                    } else {
                        creneau
                    }
                }
                file.writeText(updatedCreneaux.joinToString("\n") { c ->
                    "${c.nom},${c.jour},${c.heureDebut},${c.heureFin},${c.place}"
                })

                val file1 = File(filenamerendezvous)

                val directory = File(directoryPath)
                if (!directory.exists()) {
                    directory.mkdirs()
                }
                if (!file1.exists()) {
                    file1.createNewFile()
                }

                val newRendezvous = "${updateCreneau.nom},$patient,${updateCreneau.jour}"
                file1.appendText("$newRendezvous\n")
                return true
            } else {
                return false
            }


        }else{
            return false
        }

    }

    override fun getRendezVous(): List<RendezVous> {
        val file = File(filenamerendezvous)

        if (!file.exists()) {
            return emptyList()
        }

        return file.readLines().mapNotNull { line ->
            val parts = line.split(",")
            if (parts.size == 3) {
                try {
                    val nom = parts[0]
                    val patient = parts[1]
                    val jour = parts[2]
                    RendezVous(jour, nom , patient)
                } catch (e: Exception) {
                    null
                }
            } else {
                null
            }
        }


    }

    override fun deleteRendezVous(index: Int, creneaux: List<Creneau>): Boolean {
        var tabcre = getRendezVous().toMutableList()

        if (index-1 < 0 || index-1 >= tabcre.size) {
            return false
        }

        val deletedCreneau = tabcre.removeAt(index-1)

        val file = File(filenamerendezvous)
        val updatedLines = file.readLines().filterNot {
            val parts = it.split(",")
            parts.size == 3 &&
                    parts[0] == deletedCreneau.medecin &&
                    parts[1] == deletedCreneau.patient &&
                    parts[2] == deletedCreneau.jour
        }
        file.writeText(updatedLines.joinToString("\n"))

        var creneau = creneaux.toMutableList()

        val updateCreneau = creneau[index - 1]

        val file1 = File(filenamecrenaux)
        if (updateCreneau.place > 0) {
            val updatedCreneaux = creneau.map { creneau ->
                if (creneau == updateCreneau) {
                    creneau.copy(place = creneau.place + 1)
                } else {
                    creneau
                }
            }
            file1.writeText(updatedCreneaux.joinToString("\n") { c ->
                "${c.nom},${c.jour},${c.heureDebut},${c.heureFin},${c.place}"
            })
        }

        return true
    }


}