package com.routeplanner.api.data.service

import com.routeplanner.api.domain.model.LoginRequest
import com.routeplanner.api.domain.model.User
import com.routeplanner.api.domain.repository.UserRepository
import com.routeplanner.api.domain.service.UserService

class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override suspend fun getAllUsers(): List<User> {
        return userRepository.getAllUsers()
    }

    override suspend fun getUserById(id: Int): User? {
        return userRepository.getUserById(id)
    }

    override suspend fun loginUser(request: LoginRequest): User? {
        return userRepository.authenticate(request)
    }
}