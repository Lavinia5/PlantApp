package com.example.plantapp.domain.auht.repository.profile.repository

import com.example.plantapp.domain.auht.repository.profile.model.UserProfile
import com.example.plantapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun fetchProfile(): Flow<Response<UserProfile>>
    suspend fun updateProfile(userProfile: UserProfile): Flow<Response<Boolean>>
    suspend fun deleteProfile(): Flow<Response<Boolean>>
}