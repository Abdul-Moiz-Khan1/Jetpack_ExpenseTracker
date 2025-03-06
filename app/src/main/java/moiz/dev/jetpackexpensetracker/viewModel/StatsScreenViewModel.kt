package moiz.dev.jetpackexpensetracker.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.Entry
import moiz.dev.jetpackexpensetracker.Util
import moiz.dev.jetpackexpensetracker.data.ExpenseDatabase
import moiz.dev.jetpackexpensetracker.data.dao.ExpenseDao
import moiz.dev.jetpackexpensetracker.data.model.ExpenseEntity
import moiz.dev.jetpackexpensetracker.data.model.ExpenseSummary
import java.util.Map

class StatsScreenViewModel(dao: ExpenseDao) : ViewModel() {
    val enteries = dao.getAllExpensebyDate()

    fun getEntriesForChart(enteries:List<ExpenseSummary>): List<Entry> {
        val list = mutableListOf<Entry>()
        for (entry in enteries) {
            val formatteDate = Util.getMillisFromDate(entry.date)
            list.add(Entry(formatteDate.toFloat(), entry.totalAmount.toFloat()))
        }
    return list
    }


}

class StatsViewmodelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatsScreenViewModel::class.java)) {
            return StatsScreenViewModel(ExpenseDatabase.getDatabase(context).expenseDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
