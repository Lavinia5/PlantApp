package com.example.plantapp


import com.example.plantapp.domain.auht.repository.AuthRepository

class IsSetupCompleted(
    private val authScreenRepository: AuthRepository
) {
    operator fun invoke() = authScreenRepository.isSetupCompleted()
}