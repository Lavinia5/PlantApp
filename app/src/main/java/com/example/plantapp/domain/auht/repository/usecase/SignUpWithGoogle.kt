package com.example.plantapp.domain.auht.repository.usecase

import com.example.plantapp.domain.auht.repository.AuthRepository

class SignUpWithGoogle (
    private val authScreenRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String) = authScreenRepository.signUpWithGoogle(idToken)
}