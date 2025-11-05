package com.example.profilecardapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {
    var name by mutableStateOf("Samrat K.")
        private set

    var bio1 by mutableStateOf("Android learner")
        private set

    var bio2 by mutableStateOf("Compose beginner")
        private set

    var followers by mutableStateOf(99)

    fun saveProfile(newName: String, newBio1: String, newBio2: String) {
        name = newName
        bio1 = newBio1
        bio2 = newBio2
    }
}