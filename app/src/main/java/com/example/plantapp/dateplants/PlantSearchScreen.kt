package com.example.plantapp.dateplants
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun PlantSearchScreen(navController: NavHostController, viewModel: PlantViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }
    val plants by viewModel.plants.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Câmp de căutare
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search plants...") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Listă de plante filtrate
        LazyColumn {
            items(plants.filter { it.name.contains(query, ignoreCase = true) }) { plant ->
                // Apelarea navigării la click pe plantă
                PlantItem(plant) {
                    navController.navigate("details/${Uri.encode(plant.name)}")
                }
            }
        }
    }
}

@Composable
fun PlantItem(plant: Plant, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() } // Funcția onClick este apelată aici
    ) {
        Text(
            text = plant.name,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = plant.scientificName,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Composable
fun PlantDetailScreen(plantName: String) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Details for $plantName", style = MaterialTheme.typography.bodyMedium)

        // Exemplu de afișare a altor informații
        Text(text = "Scientific Name: Aloe barbadensis miller")
        Text(text = "Care Tips: Water once a week.")
        Text(text = "Sunlight: Indirect sunlight")
    }
}

