package moiz.dev.jetpackexpensetracker.data.model

data class ExpenseSummary(
    val type: String,
    val date: String,
    val totalAmount: Double
)