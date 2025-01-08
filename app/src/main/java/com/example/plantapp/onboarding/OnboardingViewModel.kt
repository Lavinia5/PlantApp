package com.example.plantapp.onboarding

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

import javax.inject.Inject
import androidx.lifecycle.viewModelScope

import com.example.plantapp.domain.auht.repository.profile.model.UserProfile
import com.example.plantapp.domain.auht.repository.profile.usecase.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.plantapp.utils.Response
@HiltViewModel
class
OnboardingViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
) : ViewModel() {

    var userProfile = mutableStateOf(UserProfile())
        private set

    fun updateName(name: String) {
        userProfile.value = userProfile.value.copy(name = name)
    }


    fun updateProfile() {
        viewModelScope.launch {
            profileUseCases.updateProfile(userProfile.value).collect { response ->
                when (response) {
                    is Response.Loading -> {  }
                    is Response.Success -> {
                        Log.d("DATA", "Profile updated!")
                    }
                    is Response.Error -> {
                        Log.d("DATA", "ERROR")
                    }
                }
            }
        }
    }
}