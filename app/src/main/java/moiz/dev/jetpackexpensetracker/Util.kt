package moiz.dev.jetpackexpensetracker

import java.text.SimpleDateFormat
import java.util.Locale

object Util {
    fun formatDtaetoHumanReadableFormat(date:Long):String{
        val dateFormatter = SimpleDateFormat("dd/MM/YYYY" , Locale.getDefault())
        return dateFormatter.format(date)

    }

    fun formatToTwoDecimal(d:Double):String{
        return String.format("%.2f",d)

    }
}