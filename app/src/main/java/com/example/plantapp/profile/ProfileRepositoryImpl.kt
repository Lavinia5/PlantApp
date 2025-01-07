package com.example.plantapp.profile

import com.example.plantapp.domain.auht.repository.profile.model.UserProfile
import com.example.plantapp.domain.auht.repository.profile.repository.ProfileRepository
import com.example.plantapp.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ProfileRepository {

    override suspend fun fetchProfile(): Flow<Response<UserProfile>> = flow {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            try {
                val userDocRef = firestore.collection("users").document(currentUser.uid)
                val documentSnapshot = userDocRef.get().await()

                if (documentSnapshot.exists()) {
                    val userProfile = documentSnapshot.toObject(UserProfile::class.java)
                    if (userProfile != null) {
                        emit(Response.Success(userProfile))
                    } else {
                        emit(Response.Error("User profile data is corrupted"))
                    }
                } else {
                    emit(Response.Error("Profile not found"))
                }
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: "Unknown error"))
            }
        } else {
            emit(Response.Error("User is not authenticated"))
        }
    }

    override suspend fun updateProfile(userProfile: UserProfile): Flow<Response<Boolean>> = flow {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            try {
                val userDocRef = firestore.collection("users").document(currentUser.uid)

                val updateData = mutableMapOf<String, Any>()

                if (userProfile.name.isNotEmpty()) {
                    updateData["name"] = userProfile.name
                }



                if (updateData.isNotEmpty()) {
                    userDocRef.update(updateData).await()
                }

                emit(Response.Success(true))

            } catch (e: Exception) {
                emit(Response.Error(e.message ?: "Unknown error"))
            }
        } else {
            emit(Response.Error("User is not authenticated"))
        }
    }


    override suspend fun deleteProfile(): Flow<Response<Boolean>> = flow {
        emit(Response.Error("Not implemented"))
    }

}