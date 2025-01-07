package com.example.plantapp.domain.auht.repository.usecase

import com.example.plantapp.IsSetupCompleted
import com.example.plantapp.SignInWithGoogle
import com.example.plantapp.SignOut
import com.example.plantapp.SignUp

data class AuthUseCases(
    val isUserAuthenticated: IsUserAuthenticated,
    val isSetupCompleted: IsSetupCompleted,
    val signIn: SignIn,
    val signUp: SignUp,
    val signUpWithGoogle: SignUpWithGoogle,
    val signInWithGoogle: SignInWithGoogle,
    val signInWithFacebook: SignInWithFacebook,
    val signUpWithFacebook: SignUpWithFacebook,
    val signOut: SignOut,
    val sendPasswordResetEmail: SendPasswordResetEmail,
    val sendEmailVerification: SendEmailVerification
)
