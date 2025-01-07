package com.example.plantapp.domain.auht.repository.usecase


import com.example.plantapp.domain.auht.repository.AuthRepository

class IsUserAuthenticated(
    private val authScreenRepository: AuthRepository
) {
    operator fun invoke() = authScreenRepository.isUserAuthenticatedInFirebase()
}