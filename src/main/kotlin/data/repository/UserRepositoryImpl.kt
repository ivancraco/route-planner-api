package com.routeplanner.api.data.repository

import com.routeplanner.api.config.JwtConfig
import com.routeplanner.api.config.generateAccessToken
import com.routeplanner.api.config.generateRefreshToken
import com.routeplanner.api.db.entities.UserEntity
import com.routeplanner.api.db.entities.UserSessionEntity
import com.routeplanner.api.db.tables.UserSessionTable
import com.routeplanner.api.db.tables.UserTable
import com.routeplanner.api.domain.model.LoginRequest
import com.routeplanner.api.domain.model.LoginResponse
import com.routeplanner.api.domain.model.User
import com.routeplanner.api.domain.repository.UserRepository
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.with
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import kotlin.time.Clock

class UserRepositoryImpl : UserRepository {
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

    override suspend fun login(request: LoginRequest): LoginResponse? {
        return suspendTransaction {
            val user = UserEntity
                .find {
                    UserTable.username eq request.username and (UserTable.isActive eq true)
                }.with(UserEntity::supervisor).firstOrNull()
            user?.takeIf {
                verifyPassword(
                    requestPass = request.password,
                    userPass = user.password
                )
            }?.let {
                val (accessToken, refreshToken) = buildTokensForUser(it)
                LoginResponse(
                    id = it.id.value,
                    username = it.username,
                    isSupervisor = it.isSupervisor,
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
            }
        }
    }

    override suspend fun updateTokens(refreshToken: String): LoginResponse? {
        return suspendTransaction {
            val now = Clock.System.now()

            // 1. Buscar sesión válida (token existe)
            val session = UserSessionEntity.find {
                UserSessionTable.token eq refreshToken //and (UserSessionTable.expiresAt greater now)
            }
                .with(UserSessionEntity::user)   // eager load del user
                .firstOrNull() ?: return@suspendTransaction null

            if (session.expiresAt < now) {
                session.delete()
                return@suspendTransaction null
            }

            // 2. Eliminar sesión usada (rotation: cada refresh emite uno nuevo)
            session.delete()

            // 3. Emitir nuevos tokens
            val (newAccessToken, newRefreshToken) = buildTokensForUser(session.user)
            LoginResponse(
                id = session.user.id.value,
                username = session.user.username,
                isSupervisor = session.user.isSupervisor,
                accessToken = newAccessToken,
                refreshToken = newRefreshToken
            )
        }
    }

    override suspend fun logout(refreshToken: String): Boolean {
        return suspendTransaction {
            val session = UserSessionEntity.find {
                UserSessionTable.token eq refreshToken
            }.firstOrNull() ?: return@suspendTransaction false

            session.delete()
            true
        }
    }

    override fun verifyPassword(requestPass: String, userPass: String): Boolean =
        requestPass == userPass

    override fun buildTokensForUser(user: UserEntity): Pair<String, String> {
        val accessToken = generateAccessToken(user)
        val refreshToken = generateRefreshToken()
        val now = Clock.System.now()

        UserSessionEntity.new {
            userId = user.id
            token = refreshToken
            createdAt = now
            expiresAt = now.plus(JwtConfig.REFRESH_TOKEN_EXPIRY_MS)
        }

        return Pair(accessToken, refreshToken)
    }
}