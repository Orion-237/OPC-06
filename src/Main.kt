package frameworks

import entities.Medecin
import interfaceadapters.ConsoleController
import interfaceadapters.FichierRendezVousRepository
import interfaceadapters.InMemoryMedecinRepository
import usecases.*

suspend fun main() {
    val medecinRepository = InMemoryMedecinRepository("medecins.txt")
    val rendezVousRepository = FichierRendezVousRepository("rendezvous.txt")

    val controller = ConsoleController()

    val ajouterDisponibilite = AjouterDisponibilite(medecinRepository)
    val afficherDisponibilites = AfficherDisponibilites(medecinRepository)
    val reserverRendezVous = ReserverRendezVous(medecinRepository, rendezVousRepository)
    val annulerRendezVous = AnnulerRendezVous(rendezVousRepository)

    while (true) {
        controller.afficherMenu()
        when (readln().toIntOrNull()) {
            1 -> {
                // Ajouter un médecin
                print("Nom du médecin : ")
                val nom = readln()
                medecinRepository.save(Medecin(nom))
                println("✔️ Médecin ajouté avec succès.")
            }

            2 -> {
                // Ajouter une disponibilité
                print("Nom du médecin : ")
                val nom = readln()
                print("Jour : ")
                val jour = readln()
                print("Heure : ")
                val heure = readln()
                if (ajouterDisponibilite.execute(nom, jour, heure)) {
                    println("✔️ Disponibilité ajoutée avec succès.")
                } else {
                    println("❌ Médecin introuvable.")
                }
            }

            3 -> {
                // Afficher les disponibilités
                print("Nom du médecin : ")
                val nom = readln()
                val disponibilites = afficherDisponibilites.execute(nom)
                if (disponibilites.isEmpty()) {
                    println("❌ Aucune disponibilité trouvée.")
                } else {
                    disponibilites.forEach { println("- ${it.jour} à ${it.heure}") }
                }
            }

            4 -> {
                // Réserver un rendez-vous
                print("Nom du médecin : ")
                val nomMedecin = readln()
                print("Nom du patient : ")
                val nomPatient = readln()
                print("Jour : ")
                val jour = readln()
                print("Heure : ")
                val heure = readln()
                if (reserverRendezVous.execute(nomMedecin, nomPatient, jour, heure)) {
                    println("✔️ Rendez-vous réservé avec succès.")
                } else {
                    println("❌ Échec de la réservation.")
                }
            }

            5 -> {
                // Afficher tous les rendez-vous
                val rendezVousList = rendezVousRepository.findAll()
                if (rendezVousList.isEmpty()) {
                    println("❌ Aucun rendez-vous trouvé.")
                } else {
                    rendezVousList.forEach {
                        println("- ${it.medecin.nom}, ${it.patient.nom}, ${it.disponibilite.jour} à ${it.disponibilite.heure}")
                    }
                }
            }

            6 -> {
                // Annuler un rendez-vous
                print("Nom du médecin : ")
                val nomMedecin = readln()
                print("Nom du patient : ")
                val nomPatient = readln()
                print("Jour : ")
                val jour = readln()
                print("Heure : ")
                val heure = readln()
                if (annulerRendezVous.execute(nomMedecin, nomPatient, jour, heure)) {
                    println("✔️ Rendez-vous annulé avec succès.")
                } else {
                    println("❌ Échec de l'annulation.")
                }
            }

            7 -> {
                // Quitter
                println("👋 Au revoir !")
                break
            }

            else -> println("❌ Option invalide. Veuillez réessayer.")
        }
    }
}
