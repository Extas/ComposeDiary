package com.example.diary.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.diary.datebase.Diary
import com.example.diary.datebase.DiaryDao
import kotlinx.coroutines.flow.Flow


class Repository(private val dao: DiaryDao) {

    val allDiaries: Flow<List<Diary>> = dao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(diary: Diary) {
        dao.insertAll(diary)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(diary: Diary) {
        dao.delete(diary)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun findByTitle(title: String): Flow<List<Diary>> {
        return dao.findByTitle(title)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun findById(id: Int): Flow<Diary> {
        Log.d("Repository", "findById: $id")
        return dao.findById(id)
    }
}