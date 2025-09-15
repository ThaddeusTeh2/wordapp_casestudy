package com.dx.wordapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

// Data class for Word (data)

@Entity
data class Word (
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val title : String,
    val definition : String,
    val synonym : String,
    val details : String,
    val isCompleted : Boolean = false,
    val date: LocalDateTime = LocalDateTime.now()
)