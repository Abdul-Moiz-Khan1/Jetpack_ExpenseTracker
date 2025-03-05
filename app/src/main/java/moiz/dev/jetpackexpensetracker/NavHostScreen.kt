package moiz.dev.jetpackexpensetracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavHostScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = "/home") {
        composable("/home") {
            HomeScreen(navController)
        }
        composable("/addExpense") {
            AddExpense(navController)
        }
        
    }
    
}