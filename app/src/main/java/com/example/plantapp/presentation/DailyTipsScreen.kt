package com.example.plantapp.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.plantapp.R
import com.example.plantapp.theme.PurpleGrey40
import com.example.plantapp.theme.PurpleGrey80

@Composable
fun DailyTipsScreen(
    navController: NavHostController,
    viewModel: DailyTipsViewModel = hiltViewModel()
) {
    // Obține sfatul din ViewModel
    val dailyTip by viewModel.dailyTip.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imaginea de fundal
        Image(
            painter = painterResource(id = R.drawable.background_image), // Adaugă imaginea în res/drawable
            contentDescription = null,
            contentScale = ContentScale.Crop, // Ajustează cum este afișată imaginea
            modifier = Modifier.fillMaxSize()
        )
        // Layout-ul ecranului
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dailyTip ?: "Press the button to see today's tip!",
                color = PurpleGrey40,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(onClick = { viewModel.fetchDailyTip() }) {
                Text("Get today's tip")
            }

        }
    }    }
