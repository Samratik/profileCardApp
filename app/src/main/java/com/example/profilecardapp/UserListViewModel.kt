package com.example.profilecardapp

import android.app.Application
import androidx.lifecycle.*
import com.example.profilecardapp.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserListViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getInstance(app)
    private val repo = UserRepository(db.userDao(), ApiClient.api)

    val users = repo.getLocalUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun refresh() = viewModelScope.launch {
        repo.refresh()
    }
}
