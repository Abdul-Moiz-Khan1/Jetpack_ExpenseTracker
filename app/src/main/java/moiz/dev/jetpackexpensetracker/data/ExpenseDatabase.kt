package moiz.dev.jetpackexpensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moiz.dev.jetpackexpensetracker.data.dao.ExpenseDao
import moiz.dev.jetpackexpensetracker.data.model.ExpenseEntity

@Database(entities = [ExpenseEntity::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        const val DATABASE_NAME = "expense_database"

        @JvmStatic
        fun getDatabase(context: Context): ExpenseDatabase {
            return Room.databaseBuilder(context, ExpenseDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
//                        InitDB(context)
                    }

                    fun InitDB(context: Context) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val dao = getDatabase(context).expenseDao()
                            dao.insertExpense(
                                ExpenseEntity(
                                    1,
                                    "Salary",
                                    10000.0,
                                    System.currentTimeMillis().toString(),
                                    "Income",
                                    "income"
                                )
                            )
                            dao.insertExpense(
                                ExpenseEntity(
                                    2,
                                    "Food",
                                    500.0,
                                    System.currentTimeMillis().toString(),
                                    "Food",
                                    "expense"
                                )
                            )
                            dao.insertExpense(
                                ExpenseEntity(
                                    3,
                                    "Rent",
                                    2000.0,
                                    System.currentTimeMillis().toString(),
                                    "Rent",
                                    "expense"
                                )
                            )
                            dao.insertExpense(
                                ExpenseEntity(
                                    4,
                                    "Salary",
                                    1000.0,
                                    System.currentTimeMillis().toString(),
                                    "Income",
                                    "income"
                                )
                            )
                            dao.insertExpense(
                                ExpenseEntity(
                                    5,
                                    "Food",
                                    500.0,
                                    System.currentTimeMillis().toString(),
                                    "Food",
                                    "expense"
                                )
                            )
                            dao.insertExpense(
                                ExpenseEntity(
                                    6,
                                    "Rent",
                                    2000.0,
                                    System.currentTimeMillis().toString(),
                                    "Rent",
                                    "expense"
                                )
                            )
                            dao.insertExpense(
                                ExpenseEntity(
                                    7,
                                    "Salary",
                                    1000.0,
                                    System.currentTimeMillis().toString(),
                                    "Income",
                                    "income"
                                )
                            )
                            dao.insertExpense(
                                ExpenseEntity(
                                    8,
                                    "Food",
                                    500.0,
                                    System.currentTimeMillis().toString(),
                                    "Food",
                                    "expense"
                                )
                            )
                            dao.insertExpense(
                                ExpenseEntity(
                                    9,
                                    "Rent",
                                    2000.0,
                                    System.currentTimeMillis().toString(),
                                    "Rent",
                                    "expense"
                                )
                            )
                            dao.insertExpense(
                                ExpenseEntity(
                                    10,
                                    "Salary",
                                    1000.0,
                                    System.currentTimeMillis().toString(),
                                    "Income",
                                    "income"
                                )
                            )

                        }
                    }
                })

                .build()
        }
    }
}