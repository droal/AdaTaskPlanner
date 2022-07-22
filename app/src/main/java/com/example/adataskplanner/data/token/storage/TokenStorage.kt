package org.adaschool.rest.storage

interface TokenStorage {

    fun saveToken(token: String)

    fun getToken(): String?

    fun clear()
}