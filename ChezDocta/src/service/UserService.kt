package service

import model.Disponibillite
import model.Medecin
import repository.Creneau
import repository.UserRepository
import repository.UserRepositoryImpl
import ui.green
import ui.red
import ui.reset



class UserService {

    private val userRepository: UserRepository = UserRepositoryImpl()

    fun login(nom : String, pwd : String) : Boolean{
        println("\t \t \t Tentative de connexion...")
        if(userRepository.loginMedecin(nom,pwd) == true){
            println("\t \t \t ${green}Connexion reussi ${reset}")
            return true
        }
        println("\t \t \t ${red}Connexion echoue ${reset}")
        return false

    }

    fun loginPatient(nom : String, pwd : String) : Boolean{
        println("\t \t \t Tentative de connexion...")
        if(userRepository.loginPatient(nom,pwd) == true){
            println("\t \t \t ${green}Connexion reussi ${reset}")
            return true
        }

        println("\t \t \t ${red}Connexion echoue ${reset}")
        return false
    }

    fun ajouterCreneau(nom: String, jour : String, heureDebut: String, heureFin: String ){
        println("\t \t \t Tentative d'ajout...")
        val validDateTime = FormatDate.fromStrings(jour, heureDebut,heureFin)
        if (validDateTime != null) {
            if (userRepository.addCreneux(nom, Disponibillite(validDateTime.first,validDateTime.second,validDateTime.third)) == true) {
                println("\t \t \t ${green}Créneau ajouté avec succès ! ${reset} \uD83D\uDE0A\n")
            } else {
                println("\t \t \t ${red}Erreur : Ce créneau existe déjà pour ce jour avec ce Docteur.${reset}")
            }

        }
    }

    fun getAllPlaning(nom : String) : List<Creneau>{
        return userRepository.getCreneaux(nom)
    }

    fun deleteCreneau(nom: String,index : Int) {

        if(userRepository.deleteCreneau(nom,index) == true){
            println("\t \t \t ✅ ${green}Le créneau a été supprimé avec succès !${reset}")
        }else{
            println("\t \t \t ${red}❌ Erreur : Index invalide.${reset}")
        }

    }

    fun getPlaning () : List<Creneau> {
        return userRepository.getAllCreneaux()
    }



}