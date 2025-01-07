package com.example.plantapp.di

import com.example.plantapp.IsSetupCompleted
import com.example.plantapp.SignInWithGoogle
import com.example.plantapp.SignOut
import com.example.plantapp.SignUp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.plantapp.domain.auht.repository.AuthRepository
import com.example.plantapp.auth.AuthRepositoryImpl
import com.example.plantapp.domain.auht.repository.profile.repository.ProfileRepository
import com.example.plantapp.domain.auht.repository.profile.usecase.FetchProfile
import com.example.plantapp.profile.ProfileRepositoryImpl
import com.example.plantapp.domain.auht.repository.usecase.AuthUseCases
import com.example.plantapp.domain.auht.repository.profile.usecase.ProfileUseCases
import com.example.plantapp.domain.auht.repository.profile.usecase.UpdateProfile
import com.example.plantapp.domain.auht.repository.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(auth, firestore)

    @Provides
    @Singleton
    fun provideProfileRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): ProfileRepository = ProfileRepositoryImpl(auth, firestore)

    @Provides
    @Singleton
    fun provideAuthScreenUseCase(authRepository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            isUserAuthenticated = IsUserAuthenticated(authRepository),
            isSetupCompleted = IsSetupCompleted(authRepository),
            signIn = SignIn(authRepository),
            signUp = SignUp(authRepository),
            signUpWithGoogle = SignUpWithGoogle(authRepository),
            signInWithGoogle = SignInWithGoogle(authRepository),
            signOut = SignOut(authRepository),
            sendPasswordResetEmail = SendPasswordResetEmail(authRepository),
            sendEmailVerification = SendEmailVerification(authRepository),
            signInWithFacebook = SignInWithFacebook(authRepository),
            signUpWithFacebook = SignUpWithFacebook(authRepository)
        )
    }

    @Provides
    @Singleton
    fun provideProfileScreenUseCase(profileRepository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            fetchProfile = FetchProfile(profileRepository),
            updateProfile = UpdateProfile(profileRepository),
            deleteProfile = com.example.plantapp.domain.auht.repository.profile.usecase.DeleteProfile(profileRepository)
        )
    }
}
