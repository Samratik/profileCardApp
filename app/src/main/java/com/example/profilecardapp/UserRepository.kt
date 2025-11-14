package com.example.profilecardapp

class UserRepository(
    private val dao: UserDao,
    private val api: UserApi
) {
    fun getLocalUsers() = dao.getAll()

    suspend fun refresh() {
        val remote = api.getUsers()

        remote.forEach { ru ->
            dao.insert(
                UserProfile(
                    name = ru.name,
                    bio1 = "From API",
                    bio2 = ru.username,
                    followers = 0
                )
            )
        }
    }
}
