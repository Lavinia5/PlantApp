package com.example.plantapp.domain.auht.repository.usecase


import com.example.plantapp.domain.auht.repository.AuthRepository
import com.example.plantapp.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SendPasswordResetEmail(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): Flow<Response<Boolean>> {
        return if (email.isBlank()) {
            flow { emit(Response.Error("Email cannot be blank")) }
        } else {
            authRepository.sendPasswordResetEmail(email)
        }
    }
}