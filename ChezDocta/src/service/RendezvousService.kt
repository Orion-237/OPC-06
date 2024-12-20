package service


import model.RendezVous
import repository.RendezVousRepositoryImpl
import repository.RensezVousRepository
import repository.UserRepository
import repository.UserRepositoryImpl
import ui.green
import ui.red
import ui.reset

class RendezvousService {

    private val rendezVousRepository: RensezVousRepository = RendezVousRepositoryImpl()
    private val userRepository: UserRepository = UserRepositoryImpl()


    fun addRendezvous(index: Int,patient:String) {

        val creneaux = userRepository.getAllCreneaux()
        if ( rendezVousRepository.addRendezvous(patient,index,creneaux) == true ){
            println("\t \t \t ${green}Rendez vous ajouté avec succès ! $reset \uD83D\uDE0A\n")
        }else{
            println("\t \t \t ${red}Erreur : Impossibiliter de prendre ce rendez vous.${reset}")
        }

    }

    fun getRendezvous() : List<RendezVous>{
        return rendezVousRepository.getRendezVous()
    }

    fun annuleRendezvous(index: Int) {
        val creneaux = userRepository.getAllCreneaux()
        if(rendezVousRepository.deleteRendezVous(index,creneaux) == true){
            println("\t \t \t ✅ ${green}Le rendez vous a été annulé avec succès !${reset}")
        }else{
            println("\t \t \t ${red}❌ Erreur : Index invalide.${reset}")
        }

    }
}