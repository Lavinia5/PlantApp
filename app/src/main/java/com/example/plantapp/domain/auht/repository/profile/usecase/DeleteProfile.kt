package com.example.plantapp.domain.auht.repository.profile.usecase

import com.example.plantapp.domain.auht.repository.profile.repository.ProfileRepository
import com.example.plantapp.utils.Response

import kotlinx.coroutines.flow.Flow

class DeleteProfile(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Flow<Response<Boolean>> = repository.deleteProfile()
}