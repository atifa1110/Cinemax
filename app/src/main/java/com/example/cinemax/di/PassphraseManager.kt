package com.example.cinemax.di

import android.content.Context
import net.sqlcipher.database.SQLiteDatabase
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.core.content.edit

object PassphraseManager {
    private const val PREF_NAME = "secure_prefs"
    private const val KEY = "db_passphrase"

    fun getOrCreatePassphrase(context: Context): ByteArray {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val prefs = EncryptedSharedPreferences.create(
            context,
            PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        var passphrase = prefs.getString(KEY, null)
        if (passphrase == null) {
            passphrase = generateRandomPassphrase()
            prefs.edit { putString(KEY, passphrase) }
        }

        return SQLiteDatabase.getBytes(passphrase.toCharArray())
    }

    private fun generateRandomPassphrase(): String {
        return List(32) {
            ('a'..'z') + ('A'..'Z') + ('0'..'9')
        }.flatten().shuffled().joinToString("")
    }
}
