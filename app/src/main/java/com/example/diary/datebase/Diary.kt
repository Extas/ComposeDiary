package com.example.diary.datebase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Diary(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val content: String
)