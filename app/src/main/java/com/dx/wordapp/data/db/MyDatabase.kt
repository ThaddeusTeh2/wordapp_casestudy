package com.dx.wordapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dx.wordapp.data.model.Word


@Database(
    entities = [Word::class],
    version = 1
)
@TypeConverters(DateTypeConvertor::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getWordsDao(): WordsDao

    companion object {
        const val NAME = "words_database"
    }
}