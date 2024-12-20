package ui

import service.RendezvousService
import service.UserService
import ui.composant.*


var currentUser = ""
val userService = UserService()
val rendezVousService = RendezvousService()

val blue = "\u001B[34m"
val red = "\u001B[31m"
val green = "\u001B[32m"
val reset = "\u001B[0m"

    fun BienvenuComposent(){

        val welc = """
            ${blue}
               _____ _                _____             _        
              / ____| |              |  __ \           | |       
             | |    | |__   ___ ____ | |  | | ___   ___| |_ __ _ 
             | |    | '_ \ / _ \_  / | |  | |/ _ \ / __| __/ _` |
             | |____| | | |  __// /  | |__| | (_) | (__| || (_| |
              \_____|_| |_|\___/___| |_____/ \___/ \___|\__\__,_|
                                                                 
                                                                 
${reset}
        """

        println(welc)
    }

    fun demanderRole() {
        println("""
            ##################################################
            #                                                #
            #    Êtes-vous un médecin ou un patient ?        #
            #                                                #
            ##################################################
        """)

        println("\t \t \t 1\uFE0F. Médecin")
        println("\t \t \t 2\uFE0F. Patient")

        print("\t \t \t Veuillez choisir une option (1 ou 2) : ")
        val choix = readLine()?.toIntOrNull()

        when (choix) {
            1 -> {
                println("\t \t \t Vous avez choisi : Médecin")
                ConnectionMedecin()
            }
            2 -> {
                println("\t \t \t Vous avez choisi : Patient")
                ConnectionPatient()
            }
            else -> {
                println("\t \t \t ${red}Choix invalide. Veuillez entrer 1 ou 2.${reset}")
                demanderRole()
            }
        }
    }







