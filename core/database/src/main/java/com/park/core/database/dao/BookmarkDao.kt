package com.park.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.park.core.database.model.GitUserRepoBookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT id FROM git_user_repo_bookmark")
    fun getBookmarkedIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(entity: GitUserRepoBookmarkEntity)

    @Query("DELETE FROM git_user_repo_bookmark WHERE id = :id")
    suspend fun deleteBookmark(id: Int)
}