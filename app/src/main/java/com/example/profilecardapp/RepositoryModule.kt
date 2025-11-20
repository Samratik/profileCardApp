package com.example.profilecardapp.di

import com.example.profilecardapp.UserApi
import com.example.profilecardapp.UserDao
import com.example.profilecardapp.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        dao: UserDao,
        api: UserApi
    ): UserRepository = UserRepository(dao, api)
}
