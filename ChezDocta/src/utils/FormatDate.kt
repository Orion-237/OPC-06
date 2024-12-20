import ui.red
import ui.reset
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class FormatDate(
    val date: LocalDate,
    val heureDebut: LocalTime,
    val heureFin : LocalTime
) {
    companion object {
        private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        fun fromStrings(dateStr: String, timeDebutStr: String, timeFinStr: String): Triple<LocalDate, LocalTime,LocalTime>? {
            return try {
                val parsedDate = LocalDate.parse(dateStr, dateFormatter)
                val parsedTimeDebut = LocalTime.parse(timeDebutStr, timeFormatter)
                val parsedTimeFin = LocalTime.parse(timeFinStr, timeFormatter)

                // Vérifier si la date n'est pas inférieure à aujourd'hui
                if (parsedDate.isBefore(LocalDate.now())) {
                    println("\t \t \t ${red}Erreur : La date ne doit pas être antérieure à aujourd'hui (${LocalDate.now()}).${reset}")
                    return null
                }

                // Vérifier si l'heure est entre 6:00 et 22:00
                if (parsedTimeDebut.isBefore(LocalTime.of(6, 0)) || parsedTimeDebut.isAfter(LocalTime.of(22, 0))) {
                    println("\t \t \t ${red}Erreur : L'heure doit être entre 06:00 et 22:00.${reset}")
                    return null
                }

                // Vérifier si l'heure est entre la date de debut et 22:00
                if (parsedTimeFin.isBefore(parsedTimeDebut) || parsedTimeDebut.isAfter(LocalTime.of(22, 0))) {
                    println("\t \t \t ${red}Erreur : L'heure doit être entre ${parsedTimeDebut} et 22:00.${reset}")
                    return null
                }


                Triple(parsedDate, parsedTimeDebut, parsedTimeFin)
            } catch (e: DateTimeParseException) {
                println("\t \t \t ${red}Erreur : Format invalide. Date attendue: yyyy-MM-dd, Heure attendue: HH:mm${reset}")
                null
            }
        }
    }


}