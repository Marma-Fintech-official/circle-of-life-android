//// In a new file: PersonalityResultsScreen.kt
//package com.example.circleoflife
//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.drawscope.DrawScope
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.text.*
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import kotlin.math.PI
//import kotlin.math.cos
//import kotlin.math.sin
//
//// NOTE: This data would come from the ViewModel/backend
//val mockScores = mapOf(
//    "Emotional" to 4.5, "Mental" to 4.0, "Professional" to 3.5,
//    "Spiritual" to 3.0, "Social" to 4.8, "Environmental" to 4.2, "Physical" to 5.0
//)
//val mockPersonality = mapOf(
//    "archetype" to "EXPLORER",
//    "description" to "You're curious, open to growth, and driven by discovery. While some areas are still evolving, your mindset embraces the unknown and seeks new experiences over rigid structure."
//)
//
//@Composable
//fun PersonalityResultsScreen(navController: NavController) {
//    Scaffold { paddingValues ->
//        Box(modifier = Modifier.fillMaxSize().background(
//                brush = Brush.verticalGradient(
//                colors = listOf(
//                    Color(0xFFEADDFF),
//                    Color(0xFFFEF4D5),
//                    Color.White
//                ),
//            startY = 0f,
//            endY = 3000f
//        )
//        )) {
//            Column(
//                modifier = Modifier.fillMaxSize().padding(paddingValues),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//                // Placeholder for TopAppBar if needed
//                Spacer(modifier = Modifier.height(60.dp))
//
//                RadarChart(scores = mockScores)
//
//                ResultsCard(
//                    archetype = mockPersonality["archetype"]!!,
//                    description = mockPersonality["description"]!!
//                )
//
//                Button(
//                    onClick = { /* Navigate to Home */ },
//                    modifier = Modifier.fillMaxWidth().padding(24.dp).height(56.dp),
//                    shape = RoundedCornerShape(16.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
//                ) {
//                    Text("Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalTextApi::class)
//@Composable
//fun RadarChart(scores: Map<String, Double>) {
//    val labels = scores.keys.toList()
//    val values = scores.values.toList()
//    val textMeasurer = rememberTextMeasurer()
//
//    Canvas(modifier = Modifier.fillMaxWidth().height(350.dp).padding(32.dp)) {
//        val centerX = size.width / 2
//        val centerY = size.height / 2
//        val radius = size.minDimension / 2 * 0.8f
//        val numLevels = 5
//        val numLabels = labels.size
//
//        // Draw web levels
//        (1..numLevels).forEach { level ->
//            drawWebLevel(level / numLevels.toFloat(), radius, centerX, centerY, numLabels)
//        }
//        // Draw web spokes
//        (0 until numLabels).forEach { i ->
//            val angle = 2 * PI * i / numLabels - (PI / 2)
//            val x = centerX + radius * cos(angle).toFloat()
//            val y = centerY + radius * sin(angle).toFloat()
//            drawLine(Color.LightGray.copy(alpha = 0.5f), start = Offset(centerX, centerY), end = Offset(x, y))
//        }
//
//        // Draw data path
//        val path = Path()
//        values.forEachIndexed { i, value ->
//            val scaledValue = (value / 5.0).toFloat()
//            val angle = 2 * PI * i / numLabels - (PI / 2)
//            val x = centerX + radius * scaledValue * cos(angle).toFloat()
//            val y = centerY + radius * scaledValue * sin(angle).toFloat()
//            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
//        }
//        path.close()
//
//        drawPath(path, color = Color(0xFF6A5AE0).copy(alpha = 0.3f))
//        drawPath(path, color = Color(0xFF6A5AE0), style = Stroke(width = 4f))
//
//        // Draw data points
//        values.forEachIndexed { i, value ->
//            val scaledValue = (value / 5.0).toFloat()
//            val angle = 2 * PI * i / numLabels - (PI / 2)
//            val x = centerX + radius * scaledValue * cos(angle).toFloat()
//            val y = centerY + radius * scaledValue * sin(angle).toFloat()
//            drawCircle(color = Color(0xFF6A5AE0), radius = 8f, center = Offset(x, y))
//        }
//
//        // Draw labels
//        labels.forEachIndexed { i, label ->
//            val angle = 2 * PI * i / numLabels - (PI / 2)
//            val labelRadius = radius * 1.25f
//            val x = centerX + labelRadius * cos(angle).toFloat()
//            val y = centerY + labelRadius * sin(angle).toFloat()
//            val textLayoutResult = textMeasurer.measure(
//                text = AnnotatedString(label),
//                style = TextStyle(color = Color.Gray, fontSize = 12.sp, textAlign = TextAlign.Center)
//            )
//            drawText(
//                textLayoutResult = textLayoutResult,
//                topLeft = Offset(x - textLayoutResult.size.width / 2, y - textLayoutResult.size.height / 2)
//            )
//        }
//    }
//}
//
//private fun DrawScope.drawWebLevel(fraction: Float, radius: Float, centerX: Float, centerY: Float, numSides: Int) {
//    val path = Path()
//    (0..numSides).forEach { i ->
//        val angle = 2 * PI * i / numSides - (PI / 2)
//        val r = radius * fraction
//        val x = centerX + r * cos(angle).toFloat()
//        val y = centerY + r * sin(angle).toFloat()
//        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
//    }
//    drawPath(path, color = Color.LightGray.copy(alpha = 0.5f), style = Stroke(width = 2f))
//}
//
//
//@Composable
//fun ResultsCard(archetype: String, description: String) {
//    Card(
//        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
//        shape = RoundedCornerShape(24.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = archetype,
//                fontSize = 36.sp,
//                fontWeight = FontWeight.ExtraBold,
//                style = TextStyle(
//                    brush = Brush.horizontalGradient(listOf(Color.DarkGray, Color.Black)),
//                    shadow = Shadow(color = Color.Black.copy(alpha = 0.3f), offset = Offset(2f, 4f), blurRadius = 4f)
//                )
//            )
//            Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.LightGray.copy(alpha = 0.5f))
//            Text(
//                text = description,
//                textAlign = TextAlign.Center,
//                color = Color.Gray,
//                lineHeight = 24.sp
//            )
//        }
//    }
//}