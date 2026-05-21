package com.routeplanner.api.domain.repository

import com.routeplanner.api.domain.model.LoginRequest
import com.routeplanner.api.domain.model.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun authenticate(request: LoginRequest): User?
    fun verifyPasswordAndState(requestPass: String, userPass: String, isActive: Boolean): Boolean
}