package com.routeplanner.api.domain.service

import com.routeplanner.api.domain.model.LoginRequest
import com.routeplanner.api.domain.model.LoginResponse
import com.routeplanner.api.domain.model.User

interface UserService {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun login(request: LoginRequest): LoginResponse?
    suspend fun updateTokens(refreshToken: String): LoginResponse?
    suspend fun logout(refreshToken: String): Boolean
}