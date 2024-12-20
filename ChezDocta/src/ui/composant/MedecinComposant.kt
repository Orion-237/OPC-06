package ui.composant

import ui.*

fun menuMedecin() {
    var continuer = true

    while (continuer) {
        println("""
                ##################################################
                #               ${blue}Menu du Médecin 👨‍⚕️${reset}               #
                #                                                #
                #    1️. Ajouter un créneaux 📅                  #
                #    2️. Supprimer un créneaux                   #
                #    3️. Afficher tout les créneaux              #
                #    4️. Déconnexion                             #
                #    5️. Sortir du programme                     #
                #                                                #
                ##################################################
            """)

        print("\t \t \t Choisissez une option : ")
        val choix = readLine()?.toIntOrNull()

        when (choix) {
            1 -> {
                addcreneau()
            }
            2 -> {
                supprimerCreneau()
            }
            3 -> {
                afficherJoursDisponibles()
            }
            4 -> {
                println("\t \t \t Déconnexion réussie.")
                demanderRole()
            }
            5 -> {
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
            else -> println("\t \t \t ${blue}Choix invalide. Veuillez entrer un nombre valide.${reset}")
        }
    }
}


fun ConnectionMedecin() {
    println("\t \t \t 🔒 Veuillez vous connecter en tant que Médecin 🔑")

    print("\t \t \t Nom d'utilisateur : ")
    val username = readLine() ?: throw NumberFormatException("\t \t \t \uD83D\uDE22 Entrée vide")

    print("\t \t \t Mot de passe      : ")
    val password = readLine() ?: throw NumberFormatException("\t \t \t \uD83D\uDE22 Entrée vide")

    if (userService.login(username, password) == true ){
        currentUser = username
        menuMedecin()
    }else {
        ConnectionMedecin()
    }

}

fun addcreneau() {
    println("\t \t \t 🔒 Veuillez Entrez des information correct ")

    print("\t \t \t Entrez la date sous le format AAAA-MM-JJ \uD83D\uDCC5 : ")
    val date = readLine() ?: throw NumberFormatException("\t \uD83D\uDE22 Entrée vide")

    print("\t \t \t Entrez la heure de debut sous le format HH:MM \uD83D\uDD67     : ")
    val time = readLine() ?: throw NumberFormatException("\t \uD83D\uDE22 Entrée vide")
    print("\t \t \t Entrez la heure de fin sous le format HH:MM \uD83D\uDD67     : ")
    val timefin = readLine() ?: throw NumberFormatException("\t \uD83D\uDE22 Entrée vide")

    userService.ajouterCreneau(currentUser,date,time,timefin)

}


fun afficherJoursDisponibles() {

    println("""
                ##################################################
                #     ${blue}Liste des jours et heures disponibles 🩺 ${reset}               #
                ##################################################
            """)

    val jours = userService.getAllPlaning(currentUser)
    if (jours.isEmpty()) {
        println("\t \t \t Aucun jour disponible. \uD83D\uDE22")
        return
    }

    var index = 1
    for (jour in jours){
        println("\t \t \t ${index}- Date : ${jour.jour}, Debut : ${jour.heureDebut}, Fin : ${jour.heureFin}")
        index++
    }


}

fun supprimerCreneau(){
    println("""
                ##################################################
                #     ${blue}Supprimer un creneaux  \u274C ${reset}               #
                ##################################################
            """)

    afficherJoursDisponibles()
    println("\t \t \t 🔒 Veuillez Entrez le numero du créneaux  ")

    print("\t\t\t Numero : ")
    try {
        var nbr = readLine()?.toInt() ?: throw NumberFormatException("\t\t\t \uD83D\uDE22 Entrée vide")
        userService.deleteCreneau(currentUser,nbr)
    }catch (e: NumberFormatException){
        println("\t\t\t Erreur : \uD83D\uDE21 Vous devez entrer un choix valide.")
    }

}