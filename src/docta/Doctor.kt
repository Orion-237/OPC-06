package docta

import java.time.LocalDateTime

class Doctor(val name: String, val field: String) {
    val timeFrames = mutableListOf<TimeFrame>()
    val appointments = mutableListOf<Appointment>()

    init {

        // Test information
        val t1 = LocalDateTime.now()
        var start: LocalDateTime = LocalDateTime.of(t1.year, t1.month, t1.dayOfMonth, t1.hour, 0)
        repeat(4){
            start = start.plusHours(1)
            val end = start.plusHours(1L)
            _addTimeFrame(TimeFrame(start, end))
            start = end
        }
    }



    fun bookTimeFrame(timeFrame: TimeFrame, patient: Patient){
        appointments.add(Appointment(timeFrame, patient, this))
        timeFrames.remove(timeFrame)
    }

    override fun toString(): String {
        return "Dr. $name field: $field"
    }

    private fun _addTimeFrame(timeFrame: TimeFrame) {
        if (timeFrames.any{it.overlaps(timeFrame)} || appointments.any{it.timeFrame.overlaps(timeFrame)}) {
            throw Exception("""Time frame overlaps with existing time frames or appointment
                |Make sure your time frames are separate.""".trimMargin())
        }else{
            timeFrames.add(timeFrame)
        }
    }

    fun addTimeFrame() {
        val now = LocalDateTime.now()
        try {
            print("Enter the year: ")
            val year = readLine().toString().toInt()
            if (year < now.year) {
                throw Exception("Past year")
            }
            print("Enter the month (1 - 12): ")
            val month = readLine().toString().toInt()
            print("Enter the day: ")
            val day = readLine().toString().toInt()
            var time = LocalDateTime.of(year, month, day, 0, 0)
            if (time < now) {
                throw Exception("Past day")
            }
            print("Enter the hour: ")
            val hour = readLine().toString().toInt()
            time = LocalDateTime.of(year, month, day, hour, 0)
            if (time < now) {
                throw Exception("Past hour")
            }
            print("Enter the duration in hours: ")
            val duration = readLine().toString().toInt()
            if (duration < 0) {
                throw Exception("Negative duration")
            }
            val timeFrame = TimeFrame(time, time.plusHours(duration.toLong()))
            _addTimeFrame(timeFrame)
        } catch (e: Exception){
            println(e.message)
            readln()
        }
    }

    fun displayAvailableTimeFrames(){
        if (timeFrames.size == 0){
            println("No periods available")
            return
        }
        timeFrames.forEach{
            println(it.toString())
        }
    }

    fun displayAppointments(){
        if (appointments.size == 0){
            println("No appointments")
            readln()
            return
        }
        appointments.forEach{
            println(it.toStingWithoutDoctor())
        }
    }
}