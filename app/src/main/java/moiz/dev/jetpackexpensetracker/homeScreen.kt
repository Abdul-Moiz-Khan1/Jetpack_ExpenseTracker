package moiz.dev.jetpackexpensetracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import moiz.dev.jetpackexpensetracker.ui.theme.zinc

@Composable
fun HomeScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar) = createRefs()
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
            CardItem(modifier = Modifier.constrainAs(card) {
                top.linkTo(nameRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            })
        }

    }

}

@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen(modifier: Modifier = Modifier) {
    HomeScreen()
}

@Composable
fun CardItem(modifier: Modifier = Modifier) {
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
                    text = "$5000",
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
                amount = 5000
            )
            CardRowItem(
                modifier = Modifier.align(Alignment.CenterEnd),
                title = "Expense",
                image = R.drawable.arrowupsvg,
                amount = 632
            )
        }

    }

}

@Composable
fun CardRowItem(modifier: Modifier, title: String, image: Int, amount: Int) {
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
fun TransactionList(modifier: Modifier = Modifier) {
    
}