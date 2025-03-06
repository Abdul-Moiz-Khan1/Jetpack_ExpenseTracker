package moiz.dev.jetpackexpensetracker

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Util {
    fun formatDtaetoHumanReadableFormat(date: Long): String {
        val dateFormatter = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        return dateFormatter.format(date)

    }
    fun formatDateForChart(date: Long): String {
        val dateFormatter = SimpleDateFormat("dd-MMM", Locale.getDefault())
        return dateFormatter.format(date)

    }


    fun formatToTwoDecimal(d: Double): String {
        return String.format("%.2f", d)

    }

    fun getMillisFromDate(dateformat: String): Long {
        var date = Date()
        val formatter = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        try {
            date = formatter.parse(dateformat)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date.time
    }
}