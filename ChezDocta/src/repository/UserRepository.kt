package repository

import model.Disponibillite
import model.Medecin
import model.Patient
import java.io.File
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
data class Creneau( val nom: String, val jour: LocalDate, val heureDebut: LocalTime, val heureFin: LocalTime, val place : Int)

val directoryPath = "./data"
val filenamecrenaux = "$directoryPath/creneaux.txt"
val listeMedecin = listOf(
    Medecin("goube", "12345"),
    Medecin("eric", "12345")
)

val listePatient = listOf(
    Patient("patient", "12345")
)

interface UserRepository {
    fun loginMedecin(nomm : String,pwd : String) : Boolean
    fun loginPatient(nomm : String,pwd : String) : Boolean
    fun addCreneux(nom: String, disponibilite : Disponibillite) : Boolean
    fun getCreneaux(nomm :String) : List<Creneau>
    fun getAllCreneaux() : List<Creneau>
    fun deleteCreneau(nom: String, index : Int) : Boolean

    /*
    fun getAllPlaning() : List<Medecin>
    fun updateOnePlaning(index :Int, nom: String) : Pair<LocalDate, LocalTime>
    fun getPlaning(nomm : String) : MutableList<Pair<Pair<LocalDate, LocalTime>,String>>
    fun updateMedecin(nomm : String, pair : Pair<LocalDate,LocalTime>)
    fun deleteByindex(nom: String,index :Int)
    fun loginMedecin(nomm : String,pwd : String) : Boolean
    fun loginPatient(nomm : String,pwd : String) : Boolean
    */
}

class UserRepositoryImpl : UserRepository {

    override fun loginMedecin(nom : String,pwd : String) : Boolean{
        for ((nomm, mpwd) in listeMedecin){
            if (nom == nomm && pwd == mpwd) {
                return true
            }
        }
        return false
    }

    override fun loginPatient(nom : String,pwd : String) : Boolean{
        for ((nomm, mpwd) in listePatient){
            if (nom == nomm && pwd == mpwd) {
                return true
            }
        }
        return false
    }

    override fun addCreneux(nom: String, disponibilite: Disponibillite) : Boolean{

        val file = File(filenamecrenaux)

        val directory = File(directoryPath)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        if (!file.exists()) {
            file.createNewFile()
        }

        val existingEntries = file.readLines()

        val duplicate = existingEntries.any {
            val parts = it.split(",")
            val existingNom = parts[0]
            val existingJour = LocalDate.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            existingNom == nom && existingJour == disponibilite.jour
        }

        if (duplicate) {
            return false
        } else {
            val i = 10
            val newCreneau = "$nom,${disponibilite.jour},${disponibilite.heureDebut},${disponibilite.heureFin},$i"
            file.appendText("$newCreneau\n")
            return true
        }
    }

    override fun getCreneaux(nomm: String): List<Creneau> {
        val file = File(filenamecrenaux)
        if (!file.exists()) {
            return emptyList()
        }

        return file.readLines().mapNotNull { line ->
            val parts = line.split(",")
            if (parts.size == 5) {
                try {
                    val nom = parts[0]
                    val jour = LocalDate.parse(parts[1])
                    val heureDebut = LocalTime.parse(parts[2])
                    val heureFin = LocalTime.parse(parts[3])
                    val place = parts[4].toInt()
                    if (nom == nomm){
                        Creneau(nom, jour, heureDebut, heureFin,place)
                    }else{
                        null
                    }
                } catch (e: Exception) {
                    null
                }
            } else {
                null
            }
        }
    }

    override fun deleteCreneau(nom: String ,index: Int) : Boolean {
        var tabcre = getCreneaux(nom).toMutableList()

        if (index-1 < 0 || index-1 >= tabcre.size) {
            return false
        }

        val deletedCreneau = tabcre.removeAt(index-1)

        val file = File(filenamecrenaux)
        val updatedLines = file.readLines().filterNot {
            val parts = it.split(",")
            parts.size == 5 &&
                    parts[0] == deletedCreneau.nom &&
                    parts[1] == deletedCreneau.jour.toString() &&
                    parts[2] == deletedCreneau.heureDebut.toString() &&
                    parts[3] == deletedCreneau.heureFin.toString() &&
                    parts[4] == deletedCreneau.place.toString()
        }
        file.writeText(updatedLines.joinToString("\n"))

        return true

    }

    override fun getAllCreneaux(): List<Creneau> {
        val file = File(filenamecrenaux)

        if (!file.exists()) {
            return emptyList()
        }

        return file.readLines().mapNotNull { line ->
            val parts = line.split(",")
            if (parts.size == 5) {
                try {
                    val nom = parts[0]
                    val jour = LocalDate.parse(parts[1])
                    val heureDebut = LocalTime.parse(parts[2])
                    val heureFin = LocalTime.parse(parts[3])
                    val place = parts[4].toInt()
                    Creneau(nom, jour, heureDebut, heureFin,place)
                } catch (e: Exception) {
                    null
                }
            } else {
                null
            }
        }
    }

    /*
    override fun getAllPlaning() : List<Medecin>{
        return  listeMedecin
    }

    override fun getPlaning(nomm : String) : MutableList<Pair<Pair<LocalDate, LocalTime>, String>> {
        val medecin = listeMedecin.find { it.nom == nomm }
        return medecin?.jourDisponible ?: mutableListOf()

    }

    override fun updateOnePlaning(index :Int, nom: String) : Pair<LocalDate, LocalTime> {
        val medecin = listeMedecin.find { it.nom == nom }
        val ancienneDisponibilite = medecin?.jourDisponible!![index - 1]
        val nouvelleDisponibilite = ancienneDisponibilite.copy(second = "Indisponible")
        medecin?.jourDisponible!![index - 1] = nouvelleDisponibilite

        return nouvelleDisponibilite.first
    }


    override fun deleteByindex(nomm: String,index :Int){
        val medecin = listeMedecin.find { it.nom == nomm }
        medecin?.jourDisponible?.removeAt(index-1)
    }


    override fun updateMedecin(nomm : String, pair : Pair<LocalDate,LocalTime>) {
        val medecin = listeMedecin.find { it.nom == nomm }
        medecin?.jourDisponible?.add(Pair(pair,"disponible"))

    }
*/

}