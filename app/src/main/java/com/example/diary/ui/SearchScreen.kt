package com.example.diary

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.diary.datebase.Diary
import com.example.diary.viewmodel.DiaryViewModel

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    onSearchClick: (String) -> Unit,
    onDiaryClick: (Diary) -> Unit,
    diaryList: List<Diary>?
) {

    var searchString by remember { mutableStateOf("") }
    Log.d("SearchScreen", "diaryList.size = ${diaryList?.size}")

    Scaffold(
        topBar = {
            Column {
                val keyboardController = LocalSoftwareKeyboardController.current
                OutlinedTextField(
                    value = searchString,
                    onValueChange = {
                        searchString = it
                        onSearchClick(it)
                    },
                    placeholder = {
                        Text(
                            "搜索日记",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.alpha(0.7f)
                        )
                    },
                    textStyle = MaterialTheme.typography.titleMedium,
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.inversePrimary,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions {
                        keyboardController?.hide()
                        onSearchClick(searchString)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                TabRowDefaults.Divider(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    thickness = 1.dp,
                    modifier = Modifier.padding(start = 52.dp)
                )
            }
        },
        content = {
            if (diaryList?.isEmpty() == true) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("无符合条件的日记", style = MaterialTheme.typography.titleLarge)
                }

            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(15.dp)
                ) {
                    if (diaryList != null) {
                        items(diaryList) { diary ->
                            DiaryItem(diary, onDiaryClick)
                        }
                    }
                }
            }
        },
    )
}