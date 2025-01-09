package com.example.plantapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.plantapp.R

@Composable
fun PlantQuizScreen(navController: NavHostController) {
    // State for current question and result
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswers by remember { mutableStateOf(mutableListOf<String>()) }
    var showResult by remember { mutableStateOf(false) }

    // Questions and answers
    val questions = listOf(
        Question(
            question = "What type of light do you prefer?",
            answers = listOf("Direct light", "Indirect light", "Darkness"),
            plantResult = listOf("Cactus", "Rose", "Ficus")
        ),
        Question(
            question = "How often do you like to be watered?",
            answers = listOf("Daily", "Once a week", "Very rarely"),
            plantResult = listOf("Tulips", "Orchid", "Cactus")
        ),
        Question(
            question = "What is your preferred environment?",
            answers = listOf("Desert", "Tropical forest", "Garden"),
            plantResult = listOf("Cactus", "Fern", "Rose")
        ),
        Question(
            question = "What kind of soil do you prefer?",
            answers = listOf("Sandy soil", "Rich and moist soil", "Regular potting soil"),
            plantResult = listOf("Succulent", "Tulips", "Spider Plant")
        ),
        Question(
            question = "What is your ideal temperature range?",
            answers = listOf("Hot", "Warm", "Cool"),
            plantResult = listOf("Cactus", "Monstera", "Tulips")
        ),
        Question(
            question = "Do you thrive better indoors or outdoors?",
            answers = listOf("Indoors", "Outdoors", "Both"),
            plantResult = listOf("Peace Lily", "Rose", "Jade Plant")
        ),
        Question(
            question = "How much care do you prefer?",
            answers = listOf("Minimal care", "Moderate care", "High maintenance"),
            plantResult = listOf("Cactus", "Tulips", "Orchid")
        )
    )

    // Background image
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_quiz), // Make sure to add the image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (!showResult) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = questions[currentQuestionIndex].question,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF00008B), // Dark blue color
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Display the answers
                questions[currentQuestionIndex].answers.forEachIndexed { index, answer ->
                    Button(
                        onClick = {
                            selectedAnswers.add(questions[currentQuestionIndex].plantResult[index]) // Add the corresponding plant
                            if (currentQuestionIndex < questions.size - 1) {
                                currentQuestionIndex++
                            } else {
                                showResult = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Text(answer)
                    }
                }
            }
        } else {
            // Determine the most frequent plant
            val mostCommonPlant = selectedAnswers.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key ?: "mysterious"

            // Result screen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You are a $mostCommonPlant plant!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF00008B), // Dark blue color
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Button(
                    onClick = {
                        currentQuestionIndex = 0
                        selectedAnswers.clear()
                        showResult = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Text("Restart the quiz")
                }
            }
        }
    }
}

data class Question(
    val question: String,
    val answers: List<String>,
    val plantResult: List<String>
)
