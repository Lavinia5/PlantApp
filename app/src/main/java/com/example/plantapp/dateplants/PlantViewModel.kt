package com.example.plantapp.dateplants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
class PlantViewModel : ViewModel() {
    private val _plants = MutableStateFlow<List<Plant>>(emptyList())
    val plants: StateFlow<List<Plant>> get() = _plants

    init {
        fetchPlantsFromFirebase()
    }

    private fun fetchPlantsFromFirebase() {
        val database = FirebaseDatabase.getInstance().getReference("plant")
        database.get().addOnSuccessListener { snapshot ->
            val plantList = mutableListOf<Plant>()
            snapshot.children.forEach { child ->
                val plant = child.getValue(Plant::class.java)
                if (plant != null) {
                    plantList.add(plant)
                }
            }
            _plants.value = plantList
        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Failed to load plants: ${exception.message}")
        }
    }
}
