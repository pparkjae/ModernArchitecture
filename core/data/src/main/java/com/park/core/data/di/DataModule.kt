package com.park.core.data.di

import com.park.core.data.repository.GithubUserRepository
import com.park.core.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindUserRepository(
        userRepository: GithubUserRepository,
    ): UserRepository
}