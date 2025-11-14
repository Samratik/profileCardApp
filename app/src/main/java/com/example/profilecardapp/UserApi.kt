package com.example.profilecardapp

import retrofit2.http.GET

interface UserApi {
    @GET("users")
    suspend fun getUsers(): List<RemoteUser>
}
