package moiz.dev.jetpackexpensetracker.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import moiz.dev.jetpackexpensetracker.data.ExpenseDatabase
import moiz.dev.jetpackexpensetracker.data.dao.ExpenseDao
import moiz.dev.jetpackexpensetracker.data.model.ExpenseEntity

class AddExpenseViewModel(val dao: ExpenseDao) : ViewModel() {

    suspend fun addExpense(expenseEntity: ExpenseEntity): Boolean {

        try {
            dao.insertExpense(expenseEntity)
            return true
        } catch (e: Exception) {
            Log.e("data added?", "Error adding expense: ${e.message}")
            return false
        }
    }
}

class AddExpenseViewmodelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            return AddExpenseViewModel(ExpenseDatabase.getDatabase(context).expenseDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
