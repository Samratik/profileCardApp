package com.example.profilecardapp

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    app: Application,
    private val repo: UserRepository
) : AndroidViewModel(app) {

    val users = repo.getLocalUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    var isLoading by mutableStateOf(false)
        private set

    fun refresh() = viewModelScope.launch {
        isLoading = true
        try {
            repo.refresh()
        } finally {
            isLoading = false
        }
    }
}

