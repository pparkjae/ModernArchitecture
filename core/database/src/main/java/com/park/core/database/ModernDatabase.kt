package com.park.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.park.core.database.converter.RoomConverter
import com.park.core.database.dao.BookmarkDao
import com.park.core.database.model.GitUserRepoBookmarkEntity

@Database(entities = [GitUserRepoBookmarkEntity::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverter::class)
internal abstract class ModernDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}