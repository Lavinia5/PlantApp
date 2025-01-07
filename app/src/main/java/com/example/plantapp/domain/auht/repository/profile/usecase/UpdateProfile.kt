package com.example.plantapp.domain.auht.repository.profile.usecase

import com.example.plantapp.domain.auht.repository.profile.model.UserProfile
import com.example.plantapp.domain.auht.repository.profile.repository.ProfileRepository
import com.example.plantapp.utils.Response
import kotlinx.coroutines.flow.Flow

class UpdateProfile(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userProfile: UserProfile): Flow<Response<Boolean>> = repository.updateProfile(userProfile)
}