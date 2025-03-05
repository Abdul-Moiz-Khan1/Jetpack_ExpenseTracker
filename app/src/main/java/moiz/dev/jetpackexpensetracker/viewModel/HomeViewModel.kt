package moiz.dev.jetpackexpensetracker.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import moiz.dev.jetpackexpensetracker.Util
import moiz.dev.jetpackexpensetracker.data.ExpenseDatabase
import moiz.dev.jetpackexpensetracker.data.dao.ExpenseDao
import moiz.dev.jetpackexpensetracker.data.model.ExpenseEntity

class HomeViewModel(dao: ExpenseDao) : ViewModel() {
    val expenseList = dao.getAllExpense()

    fun getBalance(expenseList: List<ExpenseEntity>): String {
        var total = 0.0
        expenseList.forEach {
            if (it.type == "income") {
                total += it.amount
            } else {
                total -= it.amount
            }
        }
        return Util.formatToTwoDecimal(total)
    }

    fun getIncome(expenseList: List<ExpenseEntity>): String {
        var total = 0.0
        expenseList.forEach {
            if (it.type == "income") {
                total += it.amount
            }
        }
        return Util.formatToTwoDecimal(total)
    }

    fun getExpense(expenseList: List<ExpenseEntity>): String {
        var total = 0.0
        expenseList.forEach {
            if (it.type == "expense") {
                total += it.amount
            }
        }
        return Util.formatToTwoDecimal(total)

    }
}

class HomeViewmodelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(ExpenseDatabase.getDatabase(context).expenseDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
