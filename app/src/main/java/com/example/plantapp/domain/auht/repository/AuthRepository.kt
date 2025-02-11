package com.example.plantapp.domain.auht.repository

import com.example.plantapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticatedInFirebase(): Flow<Response<Boolean>>
    fun isSetupCompleted(): Flow<Response<Boolean>>
    suspend fun signIn(email: String, password: String): Flow<Response<Boolean>>
    suspend fun signUp(email: String, password: String, username: String): Flow<Response<Boolean>>
    suspend fun signUpWithGoogle(idToken: String): Flow<Response<Boolean>>
    suspend fun signInWithGoogle(idToken: String): Flow<Response<Boolean>>
    suspend fun signUpWithFacebook(idToken: String): Flow<Response<Boolean>>
    suspend fun signInWithFacebook(idToken: String): Flow<Response<Boolean>>
    suspend fun signOut(): Flow<Response<Boolean>>
    suspend fun sendPasswordResetEmail(email: String): Flow<Response<Boolean>>
    suspend fun sendEmailVerification(): Flow<Response<Boolean>>
}