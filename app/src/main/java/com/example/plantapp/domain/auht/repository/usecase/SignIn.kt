package com.example.plantapp.domain.auht.repository.usecase

import com.example.plantapp.domain.auht.repository.AuthRepository

class SignIn(
    private val authScreenRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = authScreenRepository.signIn(email, password)
}