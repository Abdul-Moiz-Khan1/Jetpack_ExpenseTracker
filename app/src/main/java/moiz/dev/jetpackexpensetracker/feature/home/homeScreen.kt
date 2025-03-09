package moiz.dev.jetpackexpensetracker.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import moiz.dev.jetpackexpensetracker.R
import moiz.dev.jetpackexpensetracker.data.model.ExpenseEntity
import moiz.dev.jetpackexpensetracker.ui.theme.zinc
import moiz.dev.jetpackexpensetracker.viewModel.HomeViewModel
import moiz.dev.jetpackexpensetracker.viewModel.HomeViewmodelFactory

@Composable
fun HomeScreen(navController: NavController) {


    val viewModel = HomeViewmodelFactory(LocalContext.current).create(HomeViewModel::class.java)

    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar, add) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.topbar),
                contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }) {
                Column {
                    Text(text = "Good Morning!!", fontSize = 16.sp, color = Color.White)
                    Text(
                        text = "Abdul Moiz ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = null,
                    modifier = Modifier.align(
                        Alignment.CenterEnd
                    )
                )
            }
            val state = viewModel.expenseList.collectAsState(initial = emptyList())
            val balance = viewModel.getBalance(state.value)
            val income = viewModel.getIncome(state.value)
            val expense = viewModel.getExpense(state.value)

            CardItem(modifier = Modifier.constrainAs(card) {
                top.linkTo(nameRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            }, balance, income, expense)
            TransactionList(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(list) {
                        top.linkTo(card.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    }, list = state.value
            )
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_add),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(add) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable { navController.navigate("/addExpense") }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen(modifier: Modifier = Modifier) {
    HomeScreen(rememberNavController())
}

@Composable
fun CardItem(modifier: Modifier = Modifier, balance: String, income: String, expense: String) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(zinc)
            .padding(16.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Text(text = "Total Balance", fontSize = 16.sp, color = Color.White)
                Text(
                    text = "$$balance",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Image(
                painter = painterResource(id = R.drawable.threedots),
                contentDescription = null,
                modifier = Modifier.align(
                    Alignment.CenterEnd
                )
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            CardRowItem(
                modifier = Modifier.align(Alignment.CenterStart),
                title = "Income",
                image = R.drawable.arrowdown,
                amount = income
            )
            CardRowItem(
                modifier = Modifier.align(Alignment.CenterEnd),
                title = "Expense",
                image = R.drawable.arrowupsvg,
                amount = expense
            )
        }

    }

}

@Composable
fun CardRowItem(modifier: Modifier, title: String, image: Int, amount: String) {
    Column(modifier = modifier) {
        Row {
            Image(painter = painterResource(id = image), contentDescription = null)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = title, fontSize = 16.sp, color = Color.White)

        }
        Text(text = "$${amount}", fontSize = 20.sp, color = Color.White)
    }
}

@Composable
fun TransactionList(modifier: Modifier, list: List<ExpenseEntity>) {
    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Recent Transaction",
                    modifier = Modifier.align(Alignment.CenterStart),
                    fontSize = 20.sp,
                )
                Text(
                    text = "See all",
                    modifier = Modifier.align(Alignment.CenterEnd),
                    fontSize = 16.sp,
                )
            }
        }
        items(list.reversed()) { item ->
            TransactionItem(
                title = item.title,
                amount = item.amount.toString(),
                image = R.drawable.expense,
                date = item.date.toString(),
                color = if (item.type == "income") zinc else Color.Red
            )

        }

    }
}

@Composable
fun TransactionItem(title: String, amount: String, image: Int, date: String, color: Color) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.expense),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                colorFilter = ColorFilter.tint(Color.Green)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(text = date, fontSize = 12.sp)

            }
        }
        Text(
            text = "$${amount}",
            modifier = Modifier.align(Alignment.CenterEnd),
            fontSize = 20.sp,
            color = color
        )
    }

}