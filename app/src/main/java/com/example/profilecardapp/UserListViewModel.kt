package com.example.profilecardapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {

    val users = repo.getLocalUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun refresh() = viewModelScope.launch {
        repo.refresh()
    }
}
