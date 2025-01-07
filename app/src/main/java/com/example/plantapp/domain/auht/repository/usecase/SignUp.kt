package com.example.plantapp

import com.example.plantapp.domain.auht.repository.AuthRepository


class SignUp(
    private val authScreenRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, username: String) = authScreenRepository.signUp(email, password, username)
}