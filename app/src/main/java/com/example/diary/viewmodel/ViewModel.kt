package com.example.diary.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.example.diary.datebase.Diary
import com.example.diary.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class DiaryViewModel(private val repository: Repository) : ViewModel() {

    val allDiaries: LiveData<List<Diary>> = repository.allDiaries.asLiveData()

    val specificIdDiary: LiveData<Diary>
        get() = _specificIdDiary

    val specificTitleDiary: LiveData<List<Diary>>
        get() = _specificTitleDiary

    fun insert(diary: Diary) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(diary)
    }

    fun delete(diary: Diary) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(diary)
    }

    fun findByTitle(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.findByTitle(title).collect {
                _specificTitleDiary.postValue(it)
            }
        }
    }

    fun findById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.findById(id).collect {
                _specificIdDiary.postValue(it)
            }
        }
    }

    private var _specificIdDiary = MutableLiveData<Diary>()
    private var _specificTitleDiary = MutableLiveData<List<Diary>>()
}

class DiaryViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
