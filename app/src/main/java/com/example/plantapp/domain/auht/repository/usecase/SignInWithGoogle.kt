package com.example.plantapp

import com.example.plantapp.domain.auht.repository.AuthRepository


class SignInWithGoogle(
    private val authScreenRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String) = authScreenRepository.signInWithGoogle(idToken)
}