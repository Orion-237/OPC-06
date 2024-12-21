package docta

import java.time.LocalDateTime

data class TimeFrame(var start: LocalDateTime, var end: LocalDateTime){

    fun overlaps(timeFrame: TimeFrame): Boolean {
        return timeFrame.start in start..end || timeFrame.end in start..end ||
                start in timeFrame.start .. timeFrame.end || end in timeFrame.start .. timeFrame.end
    }

    override fun toString(): String {
        val isToday = start.isBefore(LocalDateTime.now().plusHours((24 - start.hour).toLong()))
        return "${if (isToday) "Today" else "${start.dayOfMonth}/${start.month}/${start.year}"} from ${start.toLocalTime()} to ${end.toLocalTime()}"
    }
}