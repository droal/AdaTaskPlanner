package com.example.adataskplanner.data.model.response

data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val passwordHash: String,
    val created: String,
)
