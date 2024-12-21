package entities

data class RendezVous(val jour: String, val heure: String, var estReserve: Boolean = false, var patient: String? = null){
    override fun toString(): String {
        val status = if (estReserve) "\uD83C\uDF1F Réservé par $patient" else "✨ Disponible"
        return "$jour à $heure - $status\n"
    }
}
