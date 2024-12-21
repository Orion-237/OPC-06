package ui

import entities.Medecin
import utils.MedecinFactory

fun start(){
    val medecinFactory = MedecinFactory()
    val medecins = medecinFactory.creerListeMedecins(5)

    println("\n\n \t ============================== Bienvenue sur  DoctaBook Reservation \uD83C\uDFE5\uD83D\uDCBB ==============================\n")
    do {
        println("\n\t Menu Principal ⚙\uFE0F \n\t 1. ➕ Ajouter une disponibilité \n\t 2. \uD83D\uDC41\uFE0F Consulter les disponibilités \n\t 3. ✍\uFE0F Réserver un Rendez-Vous \n\t 4. \uD83D\uDEAB Sortir du programme ")
        print("\n\t Choisissez une option : ")
        val choix : String = readln()

        when(choix.toIntOrNull()){
            1 -> {
                println("\n\t ---------- ➕ Ajouter une disponibilité d'un médecin \uD83D\uDC68\u200D⚕\uFE0F ---------- \n")

                do {
                    medecins.forEach { println("\t"+ it) }
                    print("\n\tChoisissez le numéro du médecin auquel vous voulez ajouter une disponibilité (0 pour sortir) : ")
                    val choixMedecin: String = readln()
                    when(choixMedecin.toIntOrNull()){
                        in 1 .. medecins.size -> {
                            val medecinChoisi: Medecin = medecins[choixMedecin.toInt() -  1]
                            println("\n\t Vous avez choisi le medecin : $medecinChoisi")

                            try {
                                print("\n\t Entrer le jour de la disponibilité pour ce medecin(\uD83D\uDCA1 lundi-dimanche) : ")
                                val jour : String = readln()

                                print("\n\t Entrer l'heure du créneau horaire disponible(\uD83D\uDCA1 HH:mm, ex: 15:45) : ")
                                val heure = readln()
                                medecinChoisi.ajouterDisponibilite(jour, heure)
                            }catch (e: Exception){
                                println("\n\t ${e.message} \n")
                            }
                        }
                        0 -> {}
                        else -> println("\n\t⚠\uFE0F Choix invalide. Veuillez entrer un numéro valide parmi la liste des médecins.\n")
                    }
                }while (choixMedecin != "0")

            }
            2 -> {
                println("\n\t ---------- Consulter les disponibilités d'un médecin \uD83D\uDCC5 ---------- \n")

                do {
                    medecins.forEach { println("\t"+ it) }
                    print("\n\tChoisissez le numéro du médecin auquel vous voulez voir les disponibilités (0 pour sortir) : ")
                    val choixMedecin: String = readln()
                    when(choixMedecin.toIntOrNull()){
                        in 1 .. medecins.size -> {
                            val medecinChoisi: Medecin = medecins[choixMedecin.toInt() -  1]
                            medecinChoisi.consulterDisponibilites()
                        }
                        0 -> {}
                        else -> println("\n\t⚠\uFE0F Choix invalide. Veuillez entrer un numéro valide parmi la liste des médecins.\n")
                    }
                }while (choixMedecin != "0")
            }
            3 -> {
                println("\n\t ---------- Reserver un Rendez-vous avec un médecin ✍\uFE0F ---------- \n")

                do {
                    medecins.forEach { println("\t"+ it) }
                    print("\n\tChoisissez le numéro du médecin auquel vous voulez voir les disponibilités pour reservation (0 pour sortir) : ")
                    val choixMedecin: String = readln()
                    when(choixMedecin.toIntOrNull()){
                        in 1 .. medecins.size -> {
                            val medecinChoisi: Medecin = medecins[choixMedecin.toInt() -  1]
                            medecinChoisi.consulterDisponibilites()
                            try {
                                print("\n\t Entrer le jour de la reservation choisi(\uD83D\uDCA1 lundi-dimanche) : ")
                                val jour : String = readln()

                                print("\n\t Entrer l'heure du créneau choisi(\uD83D\uDCA1 HH:mm, ex: 15:45) : ")
                                val heure = readln()

                                print("\n\t Entrer le nom du patient : ")
                                val patient = readln()

                                medecinChoisi.reserverRendezVous(jour, heure, patient)
                            }catch (e: Exception){
                                println("\n\t ${e.message} \n")
                            }
                        }
                        0 -> {}
                        else -> println("\n\t⚠\uFE0F Choix invalide. Veuillez entrer un numéro valide parmi la liste des médecins.\n")
                    }
                }while (choixMedecin != "0")
            }
            4 -> println("\n Merci d'avoir utilisé DoctaBook Reservation \uD83D\uDE80\uD83D\uDE0A à plutard!")
            else -> println("\n\t⚠\uFE0F Veuillez entrer une option valide!\n")
        }
    }while(choix != "4")
}