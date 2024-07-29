import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PieChart(
    investedAmount: Float,
    interestAmount: Float,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(modifier = modifier
            .width(200.dp)
            .height(200.dp)
            .padding(top = 8.dp)) {
            drawPieChart(investedAmount, interestAmount)
        }
        PieChartLegend()
    }
}

private fun DrawScope.drawPieChart(investedAmount: Float, interestAmount: Float) {
    val totalAmount = investedAmount + interestAmount
    val investedAngle = (investedAmount / totalAmount) * 360f
    val interestAngle = (interestAmount / totalAmount) * 360f
    val stroke= Stroke(width=100f)
    drawArc(
        color = Color.Green,
        startAngle = 0f,
        sweepAngle = investedAngle,
        useCenter = false,
        size = Size(size.width, size.height),
        topLeft = Offset(0f, 0f),
        style = stroke
    )

    drawArc(
        color = Color.Blue,
        startAngle = investedAngle,
        sweepAngle = interestAngle,
        useCenter = false,
        size = Size(size.width, size.height),
        topLeft = Offset(0f, 0f),
        style=stroke
    )
}

@Composable
fun PieChartLegend() {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(32.dp)) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(Color.Green)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text("Invested Amount")

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .size(16.dp)
                .background(Color.Blue)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text("Est. Returns")
    }
}
@Preview(showBackground = true)
@Composable
fun PieChartPreview() {
    PieChart(investedAmount = 1000f, interestAmount = 200f)
}