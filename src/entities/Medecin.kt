package entities

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class Medecin(private val id: Int, private val nom: String, private val specialite: String) {

    private val disponibilites = mutableListOf<RendezVous>()


    fun ajouterDisponibilite(jour: String, heure: String) {
        if (!verifierFormatHeure(heure)) {
            throw Exception("❌ Le format de l'heure doit être HH:mm. Ex : 15:30")
        }

        if(!verifierJourSemaine(jour)){
            throw Exception("❌ Veuillez entrer un jour de la semaine valide(lundi à dimanche)")
        }

        if (disponibilites.any { it.jour == jour && it.heure == heure }) {
            throw Exception("⚠\uFE0F Ce créneau est déjà ajouté pour ce médecin.")
        }

        disponibilites.add(RendezVous(jour, heure))
        println("\n\t✅ Créneau ajouté avec succès : $jour à $heure \n")
    }

    fun consulterDisponibilites(){
        println("\n\tDisponibilités pour $nom :")
        if (disponibilites.isEmpty()) {
            println("\n\t-- Aucune disponibilité enregistrée. --\n")
        } else {
            println("\n\t=======================================")
            disponibilites.forEachIndexed { index,dispo ->
                println("\t $index. $dispo")
            }
            println("\t=======================================\n")
        }
    }

    fun reserverRendezVous(jour: String, heure: String, patient: String){
        if (!verifierFormatHeure(heure)) {
            throw Exception("❌ Le format de l'heure doit être HH:mm. Ex : 15:30")
        }

        if(!verifierJourSemaine(jour)){
            throw Exception("❌ Veuillez entrer un jour de la semaine valide(lundi à dimanche)")
        }

        val creneau = disponibilites.find { it.jour == jour && it.heure == heure }
        if (creneau == null) {
            throw Exception("⚠\uFE0F Ce créneau n'existe pas.")
        }

        if (creneau.estReserve) {
            throw Exception("⚠\uFE0F Ce créneau est déjà réservé par le patient ${creneau.patient}.")
        }

        creneau.estReserve = true
        creneau.patient = patient.trim()
        println("\n\t✅ Rendez-vous pris avec succès pour $jour à $heure avec le $nom ! Passer ce jour pour être consulter !\n")
    }

    private fun verifierFormatHeure(heure: String): Boolean {
        return try {
            LocalTime.parse(heure, DateTimeFormatter.ofPattern("HH:mm"))
            true
        } catch (e: DateTimeParseException) {
            false
        }
    }
    private fun verifierJourSemaine(jour: String): Boolean {
        private val joursValides = setOf("lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche")
        return jour.lowercase().trim() in joursValides
    }

    override fun toString(): String {
        return "$id. $nom - $specialite"
    }
}