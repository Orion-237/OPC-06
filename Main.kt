
data class Medecin (val nom : String, val specialité: String){
    var disponibilités : MutableList<String> = mutableListOf()
    fun afficherDisponibilités(){
        if (disponibilités.isEmpty()){
            println("Pas de disponibilité")}
        else {
            println("$nom est disponible $disponibilités")
        }
    }
    fun attribuerCreneaux(medecin: Medecin, Creneaux : List<String>){
        medecin.disponibilités.addAll(Creneaux)
    }
}



fun main(){
    val medecin = Medecin("Dr )
}