package com.example.diary

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.diary.datebase.Diary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onAddClick: () -> Unit,
    diaryList: List<Diary>?,
    onDiaryClick: (Diary) -> Unit = {},
    onSearchBarClick: () -> Unit
) {

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text("日记本") },
            actions = {
                IconButton(onClick = onAddClick) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add"
                    )
                }
            }
        )
    }) {
        Spacer(modifier = Modifier.height(10.dp))
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            FilledTonalButton(
                onClick = onSearchBarClick,
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .height(50.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .alpha(0.7f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Search, contentDescription = null, Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("搜索日记", style = MaterialTheme.typography.titleMedium)
                }
            }
            if (diaryList != null) {
                if (diaryList.isEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("暂无日记", style = MaterialTheme.typography.displayMedium)
                    }

                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(15.dp)
                    ) {
                        items(diaryList) { diary ->
                            DiaryItem(diary, onDiaryClick)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DiaryItem(diary: Diary, onDiaryClick: (Diary) -> Unit) {
    Card(
        onClick = { onDiaryClick(diary) },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Log.d("DiaryItem", "diary.title: ${diary.title}")
            Text(diary.title, style = MaterialTheme.typography.titleLarge)
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}
