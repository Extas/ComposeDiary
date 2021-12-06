package com.example.diary

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.diary.datebase.Diary
import com.example.diary.ui.AddScreen
import com.example.diary.ui.Screen.*
import com.example.diary.ui.theme.DiaryTheme
import com.example.diary.viewmodel.DiaryViewModel
import com.example.diary.viewmodel.DiaryViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DiaryTheme {

                val vm: DiaryViewModel =
                    viewModel(factory = DiaryViewModelFactory((application as DiaryApplication).repository))
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Main.name) {

                    composable(Main.name) {
                        val diaryList by vm.allDiaries.observeAsState()

                        MainScreen(
                            onAddClick = { navController.navigate(Add.name) },
                            diaryList = diaryList,
                            onDiaryClick = { diary -> navController.navigate("Detail/${diary.id}") },
                            onSearchBarClick = { navController.navigate(Search.name) }
                        )
                    }

                    composable(Add.name) {

                        AddScreen(
                            popBack = { navController.popBackStack() },
                            addDiary = {
                                vm.insert(it)
                                navController.popBackStack()
                            })
                    }

                    composable(
                        route = "${Detail.name}/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getInt("id")?.let { vm.findById(it) }

                        val diary by vm.specificIdDiary.observeAsState()

                        diary?.let {
                            DetailScreen(
                                diary = it,
                                popBack = { navController.popBackStack() },
                                onDeleteClick = { diary -> vm.delete(diary) }
                            )
                        }
                    }

                    composable(Search.name) {
                        val diaryList by vm.specificTitleDiary.observeAsState()

                        SearchScreen(
                            onSearchClick = { string -> vm.findByTitle(string) },
                            onDiaryClick = { diary -> navController.navigate("Detail/${diary.id}") },
                            diaryList = diaryList
                        )
                    }
                }
            }
        }
    }
}

