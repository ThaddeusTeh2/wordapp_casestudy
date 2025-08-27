package com.dx.wordapp.data.model

import java.sql.Date


data class Word (
    val id : Int,
    val title : String,
    val definition : String,
    val synonym : String,
    val details : String,
    val isCompleted : Boolean = false,
    val date: Date
)