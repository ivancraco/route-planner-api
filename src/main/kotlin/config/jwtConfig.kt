package com.routeplanner.api.config

import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.Application
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import com.routeplanner.api.domain.model.User
import com.routeplanner.api.domain.service.UserService
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import java.util.Date

object JwtConfig {
    private val dotenv = dotenv()
    val secret =
        System.getenv("JWT_SECRET")
            ?: dotenv["JWT_SECRET"]
            ?: error("JWT_SECRET faltante")
    val issuer =
        System.getenv("JWT_ISSUER")
            ?: dotenv["JWT_ISSUER"]
            ?: error("JWT_ISSUER faltante")
    val audience =
        System.getenv("JWT_AUDIENCE")
            ?: dotenv["JWT_AUDIENCE"]
            ?: error("JWT_AUDIENCE faltante")
    val realm =
        System.getenv("JWT_REALM")
            ?: dotenv["JWT_REALM"]
            ?: error("JWT_REALM faltante")
    val claimField =
        System.getenv("JWT_CLAIM_FIELD")
            ?: dotenv["JWT_CLAIM_FIELD"]
            ?: error("JWT_CLAIM_FIELD faltante")
}

/**
 * Configura la autenticación basada en JWT para la aplicación Ktor.
 *
 * Esta función instala el sistema de autenticación de Ktor y define un esquema
 * llamado "jwt-auth", que valida los tokens JWT enviados por los clientes.
 *
 * El proceso de validación incluye:
 * - Verificación del token mediante un JWT verifier
 * - Extracción del claim configurado en [JwtConfig.claimField]
 * - Búsqueda del usuario en la base de datos mediante [UserService]
 *
 * Si el token es válido y el usuario existe, la autenticación se considera exitosa.
 * En caso contrario, la solicitud será rechazada con código 401 Unauthorized.
 *
 * @param userService servicio encargado de acceder a los datos de usuarios
 */
fun Application.configureSecurity(userService: UserService) {
    val verifier = jwtVerifier()

    install(Authentication) {
        jwt("jwt-auth") {
            realm = JwtConfig.realm
            verifier(verifier)
            validate { cred ->
                cred.payload.getClaim(
                    JwtConfig.claimField
                ).asInt()?.let {
                    userService.getUserById(it)
                }
            }
        }
    }
}

/**
 * Crea y configura un verificador de tokens JWT.
 *
 * Este verificador valida la firma y los claims principales del token,
 * asegurando que provenga de una fuente confiable y no haya sido alterado.
 *
 * Las validaciones incluyen:
 * - Firma del token usando HMAC256 con el secreto definido en [JwtConfig.secret]
 * - Coincidencia del "audience" configurado en [JwtConfig.audience]
 * - Coincidencia del "issuer" configurado en [JwtConfig.issuer]
 *
 * @return instancia de [JWTVerifier] lista para validar tokens JWT
 */
fun jwtVerifier(): JWTVerifier {
    return JWT.require(Algorithm.HMAC256(JwtConfig.secret))
        .withAudience(JwtConfig.audience)
        .withIssuer(JwtConfig.issuer)
        .build()
}

/**
 * Genera un token JWT para un usuario autenticado.
 *
 * El token incluye información del usuario y metadatos necesarios
 * para la autenticación en el servidor.
 *
 * Contiene:
 * - Audience configurado en [JwtConfig.audience]
 * - Issuer configurado en [JwtConfig.issuer]
 * - Claim con el ID del usuario ([JwtConfig.claimField])
 * - Tiempo de expiración de 5 minutos
 *
 * El token es firmado usando HMAC256 con el secreto definido en [JwtConfig.secret].
 *
 * @param user usuario para el cual se genera el token
 * @return token JWT en formato String, o null si ocurre un error durante la generación
 */
fun generateToken(user: User): String? {
    return try {
        val expTime = 300000L
        JWT.create()
            .withAudience(JwtConfig.audience)
            .withIssuer(JwtConfig.issuer)
            .withClaim(JwtConfig.claimField, user.id)
            .withExpiresAt(Date(System.currentTimeMillis() + expTime))
            .sign(Algorithm.HMAC256(JwtConfig.secret))

    } catch (e: Exception) {
        //e.printStackTrace()
        null
    }
}