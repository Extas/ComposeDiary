package com.example.diary

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diary.datebase.Diary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(diary: Diary, popBack: () -> Unit, onDeleteClick: (Diary) -> Unit) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { popBack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, null)
                }
            },
            actions = {
                IconButton(onClick = {
                    onDeleteClick(diary)
                    popBack()
                }) {
                    Icon(imageVector = Icons.Filled.Delete, null)
                }
            }
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = diary.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = diary.content, style = MaterialTheme.typography.titleMedium)
        }
    }
}