package com.example.blood_aid.util

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class secure {
     var plainText: String? = null
    private val key = "bloodaid" // Should be 16 bytes
    var cipherText: String? = null
    private val iv = "1234567890123456" // 16-byte IV for AES
    @Throws(Exception::class)
    fun encrypt() {
        try {
            val keyBytes = key.toByteArray(charset("UTF-8"))
            val secretKeySpec = SecretKeySpec(keyBytes, "AES")
            val ivParameterSpec = IvParameterSpec(iv.toByteArray(charset("UTF-8")))

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)

            val encryptedBytes = cipher.doFinal(plainText!!.toByteArray(charset("UTF-8")))
            cipherText = Base64.getEncoder().encodeToString(encryptedBytes)
        } catch (e: Exception) {
            throw Exception("Encryption error: " + e.message)
        }
    }

    @Throws(Exception::class)
    fun decrypt() {
        try {
            val keyBytes = key.toByteArray(charset("UTF-8"))
            val secretKeySpec = SecretKeySpec(keyBytes, "AES")
            val ivParameterSpec = IvParameterSpec(iv.toByteArray(charset("UTF-8")))

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)

            val encryptedBytes = Base64.getDecoder().decode(cipherText)
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            plainText = String(decryptedBytes, charset("UTF-8"))
        } catch (e: Exception) {
            throw Exception("Decryption error: " + e.message)
        }
    }
}