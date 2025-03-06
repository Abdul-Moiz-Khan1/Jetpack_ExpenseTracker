package moiz.dev.jetpackexpensetracker.feature.stats

import android.view.LayoutInflater
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import moiz.dev.jetpackexpensetracker.R
import moiz.dev.jetpackexpensetracker.Util
import moiz.dev.jetpackexpensetracker.viewModel.StatsScreenViewModel
import moiz.dev.jetpackexpensetracker.viewModel.StatsViewmodelFactory

@Composable
fun StatsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {

                Text(
                    text = "Statistics",
                    fontSize = 18.sp,
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
                    },
                    colorFilter = ColorFilter.tint(Color.Black)
                )


            }
        }
    ) {
        val viewModel =
            StatsViewmodelFactory(LocalContext.current).create(StatsScreenViewModel::class.java)
        val dataState = viewModel.enteries.collectAsState(initial = emptyList())
        Column(modifier = Modifier.padding(it)) {
            val entries = viewModel.getEntriesForChart(dataState.value)
            LineChart(entries)
        }
    }
}

@Composable
fun LineChart(entries: List<Entry>) {
 val context = LocalContext.current
    AndroidView(factory = {
        val view = LayoutInflater.from(context).inflate(R.layout.stats_line_chart , null)
        view
    } , modifier =  Modifier.fillMaxWidth().height(250.dp)){view->
        val lineChart = view.findViewById<LineChart>(R.id.linechart)
         val dataset = LineDataSet(entries, "Expense").apply {
             color = android.graphics.Color.parseColor("#FF2F7E79")
             valueTextColor = android.graphics.Color.BLACK
             lineWidth = 3f
             axisDependency = YAxis.AxisDependency.RIGHT
             setDrawFilled(true)
             mode = LineDataSet.Mode.CUBIC_BEZIER
             valueTextSize = 12f
             valueTextColor = android.graphics.Color.parseColor("#FF2F7E79")
             val drawable = ContextCompat.getDrawable(context, R.drawable.chart_gradient)
             drawable?.let {
                 fillDrawable = drawable
             }
         }
        lineChart.xAxis.valueFormatter =
            object : com.github.mikephil.charting.formatter.ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return Util.formatDateForChart(value.toLong())
                }
            }


        lineChart.data = com.github.mikephil.charting.data.LineData(dataset)
        lineChart.axisLeft.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.setDrawAxisLine(false)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.invalidate()
    }
}


