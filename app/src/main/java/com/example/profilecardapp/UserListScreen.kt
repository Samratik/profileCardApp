package com.example.profilecardapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    vm: UserListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val users by vm.users.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Users (Room + Retrofit)") },
                actions = {
                    IconButton(onClick = { vm.refresh() }) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                    }
                }
            )
        }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            items(users) { u ->
                Text(
                    text = "${u.name} – ${u.bio2}",
                    modifier = Modifier.padding(12.dp)
                )
                Divider()
            }
        }
    }
}
