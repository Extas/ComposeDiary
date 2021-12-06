package com.example.diary.ui

enum class Screen {
    Main,
    Add,
    Detail,
    Search
    ;

    companion object {
        fun fromRoute(route: String?): Screen =
            when (route?.substringBefore("/")) {
                Main.name -> Main
                Add.name -> Add
                Detail.name -> Detail
                Search.name -> Search
                else -> Main
            }
    }
}