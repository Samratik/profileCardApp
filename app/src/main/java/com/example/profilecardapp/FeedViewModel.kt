package com.example.profilecardapp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class Post(
    val id: Int,
    val author: String,
    val text: String,
    val likes: Int,
    val comments: Int,
    val isLiked: Boolean = false,
    val isDeleted: Boolean = false
)

@HiltViewModel
class FeedViewModel @Inject constructor() : ViewModel() {

    private val _posts = MutableStateFlow(
        listOf(
            Post(
                id = 1,
                author = "Aruzhan",
                text = "Finished 3 km run in the morning 💪",
                likes = 10,
                comments = 2
            ),
            Post(
                id = 2,
                author = "Marat",
                text = "Leg day today. My muscles are crying 😭",
                likes = 4,
                comments = 1
            ),
            Post(
                id = 3,
                author = "Aliya",
                text = "Yoga + stretching = perfect evening ✨",
                likes = 7,
                comments = 3
            )
        )
    )
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    fun toggleLike(id: Int) {
        _posts.update { list ->
            list.map { post ->
                if (post.id == id) {
                    val nowLiked = !post.isLiked
                    post.copy(
                        isLiked = nowLiked,
                        likes = if (nowLiked) post.likes + 1 else post.likes - 1
                    )
                } else post
            }
        }
    }

    fun deletePost(id: Int) {
        _posts.update { list ->
            list.map { post ->
                if (post.id == id) post.copy(isDeleted = true) else post
            }
        }
    }
}
