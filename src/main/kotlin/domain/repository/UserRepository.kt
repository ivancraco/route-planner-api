package com.routeplanner.api.domain.repository

import com.routeplanner.api.db.entities.UserEntity
import com.routeplanner.api.domain.model.LoginRequest
import com.routeplanner.api.domain.model.LoginResponse
import com.routeplanner.api.domain.model.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun login(request: LoginRequest): LoginResponse?
    suspend fun updateTokens(refreshToken: String): LoginResponse?
    suspend fun logout(refreshToken: String): Boolean
    fun verifyPassword(requestPass: String, userPass: String): Boolean
    fun buildTokensForUser(user: UserEntity): Pair<String, String>
}