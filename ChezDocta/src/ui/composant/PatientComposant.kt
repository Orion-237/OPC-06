package ui.composant

import model.Medecin
import ui.*

fun menuPatient() {
    var continuer = true

    while (continuer) {
        println("""
                ##################################################
                #               Menu du Patient                  #
                #                                                #
                #    1. 📅 Afficher les créneaux disponibles     #
                #    2. ✍️ Réserver un rendez-vous               #
                #    3. 🗒️ Afficher tous vos rendez-vous pris    #
                #    4. 🗒️ Annuler un rendez vous                #
                #    5. Déconnexion                              #
                #    6. Sortir du programme                      #
                #                                                #
                ##################################################
            """)

        print("\t\t\t Choisissez une option : ")
        val choix = readLine()?.toIntOrNull()

        when (choix) {
            1 -> {
                afficherToutJour()
            }
            2 -> {
                prendreRendezVous()
            }
            3 -> {
                afficheRendezvous()
            }
            4 -> {
                annulerRendezvous()
            }
            5 -> {
                println("\t\t\t Déconnexion réussie.")
                demanderRole()
            }
            6 -> {
                println("\t \t \t \n" +
                        "                                    _             \n" +
                        "     /\\                            (_)            \n" +
                        "    /  \\  _   _ _ __ _____   _____  _ _ __        \n" +
                        "   / /\\ \\| | | | '__/ _ \\ \\ / / _ \\| | '__|       \n" +
                        "  / ____ \\ |_| | | |  __/\\ V / (_) | | |_ _ _ _ _ \n" +
                        " /_/    \\_\\__,_|_|  \\___| \\_/ \\___/|_|_(_|_|_|_|_)\n" +
                        "                                                  \n" +
                        "                                                  \n")

                continuer = false
            }
            else -> println("\t\t\t Choix invalide. Veuillez entrer un nombre valide.")
        }
    }
}



fun ConnectionPatient(){
    println("\t\t\t 🔒 Veuillez vous connecter en tant que Patient 🔑")

    print("\t\t\t Nom d'utilisateur : ")
    val username = readLine() ?: throw NumberFormatException("\t\t\t \uD83D\uDE22 Entrée vide")

    print("\t\t\t Mot de passe      : ")
    val password = readLine() ?: throw NumberFormatException("\t\t\t \uD83D\uDE22 Entrée vide")

    if (userService.loginPatient(username, password) == true ){
        currentUser = username
        menuPatient()
    }else {
        ConnectionPatient()
    }

}

fun afficherToutJour() {

    println("""
                ##################################################
                #     ${blue}Disponibiliter des Docteurs 🩺 $reset               #
                ##################################################
            """)

    val jours = userService.getPlaning()

    if (jours.isEmpty()) {
        println("\t \t \t Aucun jour disponible. \uD83D\uDE22")
        return
    }

    var index = 1
    for (jour in jours){
        println("\t \t \t ${index}- Date : ${jour.jour}, Debut : ${jour.heureDebut}, Fin : ${jour.heureFin}, Nombre de place disponible : ${jour.place}")
        index++
    }

}

fun prendreRendezVous() {
    println("""
        ############################################################
        #                                                          #
        #          🗓️  Choisissez une disponibilité 🕒              #
        #                                                          #
        ############################################################
    """)

    afficherToutJour()
    print("\t\t\t 👉 Choisir la periode du rendez vous : ")

    try {
        val choix = readLine()?.toIntOrNull() ?: throw NumberFormatException("\t \uD83D\uDE22 Entrée vide")
        rendezVousService.addRendezvous(choix, currentUser)
    }catch (e: NumberFormatException){
        println("\t Erreur : \uD83D\uDE21 Vous devez entrer un choix valide.")
    }


}

fun afficheRendezvous(){

    println("""
                ##################################################
                #     ${blue}Liste des rendez vous pris $reset               #
                ##################################################
            """)
    val rendezvous = rendezVousService.getRendezvous()
    if (rendezvous.isEmpty()){
        println("\t\t\t Aucun rendez vous disponible.")
        return
    }
    var index = 1
    for(rendezvou in rendezvous){
        println("\t\t\t ${index}- Date : ${rendezvou.jour}, Medecin : ${rendezvou.medecin},  Patient : ${rendezvou.patient} ")
        index++
    }
}

fun annulerRendezvous() {

    println("""
                ##################################################
                #     ${blue}🗓️  Annuler un rendez vous  🕒  $reset               #
                ##################################################
            """)

    afficheRendezvous()
    val rendezvous = rendezVousService.getRendezvous()

    if(rendezvous.isEmpty()){
        return
    }

    print("\t\t\t 👉 Entrez le numéro du rendez vous choisi : ")

    try {
        val choix = readLine()?.toIntOrNull() ?: throw NumberFormatException("\t \uD83D\uDE22 Entrée vide")
        if (rendezvous[choix-1].patient == currentUser ) {
            rendezVousService.annuleRendezvous(choix)
        }else{
            println("Vous ne pouvez qu'annuler vos rendez vous ")
        }
    }catch (e: NumberFormatException){
        println("\t Erreur : \uD83D\uDE21 Vous devez entrer un choix valide.")
    }

}