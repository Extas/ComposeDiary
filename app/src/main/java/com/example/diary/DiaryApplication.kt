package com.example.diary

import android.app.Application
import com.example.diary.datebase.DiaryDatabase
import com.example.diary.repository.Repository

class DiaryApplication: Application() {
    private val database by lazy { DiaryDatabase.getDatabase(this) }
    val repository by lazy { Repository(database.getDiaryDao()) }
}