package com.example.plantapp.domain.auht.repository.profile.usecase

import com.example.plantapp.domain.auht.repository.profile.model.UserProfile
import com.example.plantapp.domain.auht.repository.profile.repository.ProfileRepository
import com.example.plantapp.utils.Response
import kotlinx.coroutines.flow.Flow

class FetchProfile(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Flow<Response<UserProfile>> = repository.fetchProfile()
}