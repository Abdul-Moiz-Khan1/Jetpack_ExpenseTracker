@file:OptIn(ExperimentalMaterial3Api::class)

package moiz.dev.jetpackexpensetracker.feature.add_expense

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import moiz.dev.jetpackexpensetracker.R
import moiz.dev.jetpackexpensetracker.Util
import moiz.dev.jetpackexpensetracker.data.model.ExpenseEntity
import moiz.dev.jetpackexpensetracker.viewModel.AddExpenseViewModel
import moiz.dev.jetpackexpensetracker.viewModel.AddExpenseViewmodelFactory
import java.util.Locale

@Composable
fun AddExpense(navController: NavController) {

    val viewModel =
        AddExpenseViewmodelFactory(LocalContext.current).create(AddExpenseViewModel::class.java)

    val coroutineScope = rememberCoroutineScope()

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
                .padding(top = 64.dp, start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }) {
                Text(
                    text = "Add Expense",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter),
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterStart).clickable {
                        navController.popBackStack()
                    }
                )
            }
            DataForm(modifier = Modifier
                .padding(top = 20.dp)
                .constrainAs(card) {
                    top.linkTo(nameRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }, onAddExpenseClicked = {
                    coroutineScope.launch {
                        if(viewModel.addExpense(it)){
                         navController.popBackStack()
                        }
                    }

            })
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddExpense(modifier: Modifier = Modifier) {
    AddExpense(rememberNavController())
}

@Composable
fun DataForm(modifier: Modifier = Modifier, onAddExpenseClicked: (model: ExpenseEntity) -> Unit) {

    val name = remember {
        mutableStateOf("")
    }
    val amount = remember {
        mutableStateOf("")
    }
    val date = remember {
        mutableStateOf(0L)
    }
    val dateDialogVisibility = remember {
        mutableStateOf(false)
    }
    val category = remember {
        mutableStateOf("Netflix")
    }
    val type = remember {
        mutableStateOf("income")
    }


    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        //name
        Text(text = "Name", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(8.dp))

        //amount
        Text(text = "Amount", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedTextField(
            value = amount.value,
            onValueChange = { amount.value = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(8.dp))

        //date
        Text(text = "Date", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedTextField(
            value = if (date.value == 0L) {
                ""
            } else {
                Util.formatDtaetoHumanReadableFormat(date.value)
            },
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dateDialogVisibility.value = true },
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Black,
                disabledTextColor = Color.Black,
            )
        )
        Spacer(modifier = Modifier.size(8.dp))

        //dropdown category
        Text(text = "Category", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.size(4.dp))
        ExpenseDropdown(
            list = listOf(
                "Netflix",
                "Paypal",
                "Grocery",
                "Salary",
                "Salary",
                "Upwork",
                "Donations"
            ), onItemSelect = {
                category.value = it
            }
        )
        Spacer(modifier = Modifier.size(8.dp))

        //type
        Text(text = "Type", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.size(4.dp))
        ExpenseDropdown(
            list = listOf(
                "Income",
                "Expense",
                ), onItemSelect = {
                type.value = it
            }
        )
        Spacer(modifier = Modifier.size(8.dp))


        //add button
        Button(onClick = {
            val model = ExpenseEntity(
                null,
                name.value,
                amount.value.toDoubleOrNull() ?: 0.0,
                Util.formatDtaetoHumanReadableFormat(date.value),
                category.value,
                type.value.lowercase(Locale.getDefault())
            )
            onAddExpenseClicked(model)
        }, modifier = Modifier.fillMaxWidth()
        )

        {
            Text(text = "Add Expense")


        }

    }
    if (dateDialogVisibility.value) {
        ExpenseDatePicker(
            onDateSelected = {
                date.value = it
                dateDialogVisibility.value = false
            },
            onDismiss = { dateDialogVisibility.value = false })
    }
}



@Composable
fun ExpenseDatePicker(
    onDateSelected: (date: Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                Text(text = "Confirm")
            }
        }, dismissButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)

    }
}

@Composable
fun ExpenseDropdown(list: List<String>, onItemSelect: (item: String) -> Unit) {
    val expanded = remember {
        mutableStateOf(false)
    }
    val selectedItem = remember {
        mutableStateOf(list[0])
    }
    ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = { expanded.value = it }) {
        TextField(
            value = selectedItem.value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)

            }
        )
        ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = { }) {
            list.forEach { item ->
                DropdownMenuItem(text = { Text(text = item) }, onClick = {
                    selectedItem.value = item
                    onItemSelect(item)
                    expanded.value = false
                }
                )

            }

        }
    }
}