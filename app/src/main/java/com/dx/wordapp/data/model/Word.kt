package com.dx.wordapp.data.model

import java.time.LocalDateTime

// Data class for Word (data)

data class Word (
    val id : Int? = null,
    val title : String,
    val definition : String,
    val synonym : String,
    val details : String,
    val isCompleted : Boolean = false,
    val date: LocalDateTime = LocalDateTime.now()
)