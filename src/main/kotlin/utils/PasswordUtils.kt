package com.routeplanner.api.utils

import java.security.MessageDigest

object PasswordUtils {

    fun md5(password: String): String {
        val digest = MessageDigest.getInstance("MD5")
            .digest(password.toByteArray(Charsets.UTF_8))

        return digest.joinToString("") { "%02x".format(it) }
    }

    fun verifyMd5(password: String, hashStored: String): Boolean {
        val calculatedHash = md5(password)

        return MessageDigest.isEqual(
            calculatedHash.toByteArray(Charsets.US_ASCII),
            hashStored.trim().lowercase().toByteArray(Charsets.US_ASCII)
        )
    }
}