package com.example.plantapp.domain.auht.repository.usecase


import com.example.plantapp.domain.auht.repository.AuthRepository
import com.example.plantapp.utils.Response
import kotlinx.coroutines.flow.Flow

class SendEmailVerification (
  private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Flow<Response<Boolean>> {
        return authRepository.sendEmailVerification()
    }
}