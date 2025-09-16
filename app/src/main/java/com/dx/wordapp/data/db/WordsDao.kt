package com.dx.wordapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dx.wordapp.data.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {
    @Query("SELECT * FROM word")
    fun getAllWords(): Flow<List<Word>>

    // Room stores Boolean as INTEGER (0 = false, 1 = true) (SQLite takes integer 0/1 as Boolean)
    @Query("SELECT * FROM word WHERE isCompleted = 1")
    fun getCompletedWords(): Flow<List<Word>>

    // Room stores Boolean as INTEGER (0 = false, 1 = true) (SQLite takes integer 0/1 as Boolean)
    @Query("SELECT * FROM word WHERE isCompleted = 0")
    fun getUnlearnedWords(): Flow<List<Word>>

    @Insert
    fun addWord(word: Word)

    @Query("SELECT * FROM word WHERE id = :id")
    fun getWord(id: Int): Word?

    @Update
    fun updateWord(word: Word)

    @Query("DELETE FROM word WHERE id = :id")
    fun deleteWord(id: Int)
}