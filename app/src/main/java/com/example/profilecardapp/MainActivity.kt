@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.profilecardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

@Composable
fun ProfileScreen() {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Card") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            ProfileCard(snackbarHostState = snackbarHostState)
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