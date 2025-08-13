// In a new file: PersonalityQuestionnaireScreen.kt
package com.example.circleoflife

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// NOTE: In a real app, this data would come from the ViewModel/backend
val questionnaireCategories = listOf(
    Pair("Emotional", "You feel in control of your emotions most of the time."),
    Pair("Emotional", "You allow yourself to experience emotions without judgment."),
    Pair("Mental", "You feel mentally focused and clear throughout the day."),
    Pair("Mental", "You regularly challenge your mind with new ideas or learning."),
    // ... add all 14 questions here
)
val answerOptions = listOf(
    Pair("Strongly Agree", "😁"),
    Pair("Agree", "🙂"),
    Pair("Neutral", "😐"),
    Pair("Disagree", "😕"),
    Pair("Strongly Disagree", "😠")
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun PersonalityQuestionnaireScreen(navController: NavController) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    // In a real app, answers would be stored in a ViewModel
    val answers = remember { mutableStateListOf<Int>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personality Mapping", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (currentQuestionIndex > 0) currentQuestionIndex--
                        else navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFEADDFF),
                        Color(0xFFFEF4D5),
                        Color.White
                    ),
                    startY = 0f,
                    endY = 3000f
                )
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // You can reuse your ProgressIndicator here
                LinearProgressIndicator(
                    progress = { (currentQuestionIndex + 1) / questionnaireCategories.size.toFloat() },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Animated content for question transition
                AnimatedContent(
                    targetState = currentQuestionIndex,
                    transitionSpec = {
                        slideInHorizontally { width -> width } with slideOutHorizontally { width -> -width }
                    }, label = "question transition"
                ) { index ->
                    QuestionCard(
                        category = questionnaireCategories[index].first,
                        question = questionnaireCategories[index].second,
                        progress = "${index + 1}/${questionnaireCategories.size}"
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                answerOptions.forEachIndexed { index, option ->
                    AnswerButton(
                        text = option.first,
                        emoji = option.second,
                        onClick = {
                            answers.add(5 - index) // Store answer (5 for Strongly Agree, etc.)
                            if (currentQuestionIndex < questionnaireCategories.size - 1) {
                                currentQuestionIndex++
                            } else {
                                // All questions answered, navigate to results
                                // In a real app, you would submit `answers` to your backend
                                // and navigate with the results. For now, we navigate to a placeholder.
                                navController.navigate("personalityResults")
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun QuestionCard(category: String, question: String, progress: String) {
    Box(contentAlignment = Alignment.TopCenter) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFF6A5AE0).copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = question,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = progress, color = Color.Gray)
            }
        }
        // Category "Pill"
        Card(
            shape = RoundedCornerShape(50),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFF6A5AE0))
        ) {
            Text(
                text = "⚡️ $category",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color(0xFF6A5AE0),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun AnswerButton(text: String, emoji: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(60.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.7f)),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, color = Color.Black, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = emoji, fontSize = 24.sp)
        }
    }
}