package com.park.core.database.di

import android.content.Context
import androidx.room.Room
import com.park.core.database.ModernDatabase
import com.park.core.database.dao.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): ModernDatabase = Room.databaseBuilder(
        context,
        ModernDatabase::class.java,
        "modern-database",
    ).build()

    @Provides
    fun providesTopicsDao(
        database: ModernDatabase,
    ): BookmarkDao = database.bookmarkDao()
}
