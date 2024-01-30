package com.example.pixelshop.data.auth.repository

import com.example.pixelshop.util.Resource
import com.google.firebase.auth.FirebaseUser
import com.example.pixelshop.model.User

interface FirebaseAuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(user: User, password: String)
    suspend fun retrieveData(): Resource<User>
    suspend fun updateData(user: User)
    suspend fun forgotPassword(email: String): Resource<String>
    fun logout()
}