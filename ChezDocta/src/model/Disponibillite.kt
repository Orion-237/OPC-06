package model

import java.time.LocalDate
import java.time.LocalTime

data class Disponibillite(val jour : LocalDate, val heureDebut : LocalTime, val heureFin : LocalTime)
