package com.example.adataskplanner.data.model.request

data class AuthenticationBodyDto(
    val email: String = "santiago@mail.com",
    val password: String = "passw0rd"
)
