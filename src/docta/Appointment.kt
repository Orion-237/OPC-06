package docta

data class Appointment(val timeFrame: TimeFrame, val patient: Patient, val doctor: Doctor){
    override fun toString(): String {
        return "Appointment of Dr. ${doctor.name} with patient ${patient.name} on $timeFrame"
    }

    fun toStingWithoutDoctor(): String = "Appointment with patient ${patient.name} on $timeFrame"
}