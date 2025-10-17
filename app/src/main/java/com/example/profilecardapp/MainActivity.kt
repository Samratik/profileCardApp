@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.profilecardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.profilecardapp.ui.theme.ProfileCardAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCardAppTheme {
                ProfileScreen()
            }
        }
    }
}

data class Follower(val id: Int, val name: String, val avatar: Int = R.drawable.avatar)

@Composable
fun ProfileScreen() {
    val snackbarHostState = remember { SnackbarHostState() }

    val followersList = remember {
        mutableStateListOf(
            Follower(1, "Aruzhan"),
            Follower(2, "Marat"),
            Follower(3, "Aliya"),
            Follower(4, "Kairat"),
            Follower(5, "Dina")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Card") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StoriesRow(followers = followersList)

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ProfileCard(snackbarHostState = snackbarHostState)
            }

            FollowersList(
                followers = followersList,
                onRemove = { f -> followersList.remove(f) }
            )
        }
    }
}

@Composable
fun ProfileCard(snackbarHostState: SnackbarHostState) {
    var following by rememberSaveable { mutableStateOf(false) }
    var followers by rememberSaveable { mutableStateOf(99) }
    var showUnfollow by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val btnColor: Color by animateColorAsState(
        targetValue = if(following) Color.Gray else Color(0xFF1E88E5),
        label = "followButtonColor"
    )

    Card(
        modifier = Modifier.width(150.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "smth",
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.height(16.dp))

            Text(
                text = "Samrat K.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF263238)
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Android learner",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = "Compose beginner",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Followers: $followers",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (following) {
                        showUnfollow = true
                    } else {
                        following = true
                        followers += 1
                        scope.launch {
                            val msg = if (followers == 100)
                                "🎉 Congrats! You are the 100th follower"
                            else
                                "Followed"
                            snackbarHostState.showSnackbar(msg)
                        }
                    }
                },
                modifier = Modifier.width(120.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = btnColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (following) "Unfollow" else "Follow")
            }
            if (showUnfollow) {
                AlertDialog(
                    onDismissRequest = { showUnfollow = false },
                    title = { Text("Unfollow?") },
                    text  = { Text("Are you sure you want to unfollow?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                following = false
                                followers = (followers - 1).coerceAtLeast(0)
                                showUnfollow = false
                                scope.launch {
                                    snackbarHostState.showSnackbar("Unfollowed")
                                }
                            }
                        ) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showUnfollow = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun StoriesRow(followers: List<Follower>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(followers, key = { it.id }) { f ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(f.avatar),
                    contentDescription = f.name,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.height(4.dp))
                Text(f.name, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun FollowersList(
    followers: List<Follower>,
    onRemove: (Follower) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 8.dp)
    ) {
        item {
            Text(
                "Followers",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
        }

        items(followers, key = { it.id }) { f ->
            FollowerItem(follower = f, onRemove = { onRemove(f) })
        }
    }
}

@Composable
fun FollowerItem(
    follower: Follower,
    onRemove: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(follower.avatar),
                    contentDescription = follower.name,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(8.dp))
                Text(follower.name, fontWeight = FontWeight.Medium)
            }
            OutlinedButton(onClick = onRemove, shape = RoundedCornerShape(8.dp)) {
                Text("Remove")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreview() {
    ProfileCardAppTheme {
        Surface(color = Color(0xFFF5F5F5), modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ProfileScreen()            }
        }
    }
}