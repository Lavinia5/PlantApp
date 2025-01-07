package com.example.plantapp.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantapp.domain.auht.repository.profile.model.UserProfile
import com.example.plantapp.domain.auht.repository.profile.usecase.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.plantapp.utils.Response
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    private val _userProfileState = MutableStateFlow<Response<UserProfile>>(Response.Loading)
    val userProfileState: StateFlow<Response<UserProfile>> = _userProfileState

    init {
        fetchProfile()
    }

    fun fetchProfile() {
        viewModelScope.launch {
            profileUseCases.fetchProfile().collect { response ->
                _userProfileState.value = response
            }
        }
    }

    fun getUserProfileFromState(state: Response<UserProfile>): UserProfile? {
        return when (state) {
            is Response.Success -> state.data
            else -> null
        }
    }

}

