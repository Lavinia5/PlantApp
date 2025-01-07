package com.example.plantapp.domain.auht.repository.usecase


import com.example.plantapp.domain.auht.repository.AuthRepository

class SignUpWithFacebook(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(accessToken: String) = authRepository.signUpWithFacebook(accessToken)
}