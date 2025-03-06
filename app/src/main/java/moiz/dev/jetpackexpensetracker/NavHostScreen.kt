package moiz.dev.jetpackexpensetracker

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import moiz.dev.jetpackexpensetracker.feature.add_expense.AddExpense
import moiz.dev.jetpackexpensetracker.feature.home.HomeScreen
import moiz.dev.jetpackexpensetracker.feature.stats.StatsScreen
import moiz.dev.jetpackexpensetracker.ui.theme.zinc

@Composable
fun NavHostScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "/home") {
        composable("/home") {
            HomeScreen(navController)
        }
        composable("/addExpense") {
            AddExpense(navController)
        }
        composable("/stats") {
            StatsScreen(navController = navController)
        }
    }
}

data class NavItem(
    val route: String,
    val icon: Int
)

@Composable
fun NavigationBottomBar(navController: NavController, items: List<NavItem>) {
    val NavBackstackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = NavBackstackEntry.value?.destination?.route

    BottomAppBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = {
                    Icon(painter = painterResource(id = item.icon), contentDescription = null)
                },
                alwaysShowLabel = false,
                colors =
                NavigationBarItemDefaults.colors(
                    selectedIconColor = zinc,
                    selectedTextColor = zinc,
                )

            )
        }
    }

}