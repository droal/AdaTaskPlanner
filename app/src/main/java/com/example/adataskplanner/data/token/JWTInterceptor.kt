package com.example.adataskplanner.data.token

import okhttp3.Interceptor
import okhttp3.Response
import org.adaschool.rest.storage.TokenStorage

class JWTInterceptor(private val tokenStorage: TokenStorage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = tokenStorage.getToken()
        if (token != null) {
            request.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(request.build())
    }

}