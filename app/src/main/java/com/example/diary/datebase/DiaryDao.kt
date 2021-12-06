package com.example.diary.datebase

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary")
    fun getAll(): Flow<List<Diary>>

    @Query("SELECT * FROM diary WHERE title LIKE '%' || :title  || '%'")
    fun findByTitle(title: String): Flow<List<Diary>>

    @Query("SELECT * FROM diary WHERE id = :id")
    fun findById(id: Int): Flow<Diary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg diaries: Diary)

    @Delete
    fun delete(vararg diaries: Diary)
}