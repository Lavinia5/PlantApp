package com.example.plantapp

import com.example.plantapp.domain.auht.repository.AuthRepository


class SignOut(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.signOut()
}