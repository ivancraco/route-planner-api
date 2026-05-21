package com.routeplanner.api.data.repository

import com.routeplanner.api.db.entities.UserEntity
import com.routeplanner.api.db.tables.UserTable
import com.routeplanner.api.domain.model.LoginRequest
import com.routeplanner.api.domain.model.User
import com.routeplanner.api.domain.repository.UserRepository
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

class UserRepositoryImpl: UserRepository {
    override suspend fun getAllUsers(): List<User> {
        return suspendTransaction {
            UserEntity.all().map {
                User(
                    id = it.id.value,
                    username = it.username,
                    fullName = it.fullName
                )
            }
        }
    }

    override suspend fun getUserById(id: Int): User? {
        return suspendTransaction {
            UserEntity.findById(id)?.toUser()
        }
    }

    override suspend fun authenticate(request: LoginRequest): User? {
        return suspendTransaction {
            val user = UserEntity.find { UserTable.username eq request.username }.firstOrNull()
            user?.takeIf {
                verifyPasswordAndState(
                    requestPass = request.password,
                    userPass = user.password,
                    isActive = user.isActive
                )
            }?.let {
                User(
                    id = user.id.value,
                    username = user.username,
                    fullName = user.fullName
                )
            }
        }
    }

    override fun verifyPasswordAndState(requestPass: String, userPass: String, isActive: Boolean): Boolean =
        requestPass == userPass && isActive
}