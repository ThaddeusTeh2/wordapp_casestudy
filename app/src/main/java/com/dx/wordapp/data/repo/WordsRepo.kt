package com.dx.wordapp.data.repo

import com.dx.wordapp.data.db.WordsDao
import com.dx.wordapp.data.model.Word
import kotlinx.coroutines.flow.Flow

// Main class to get getAll add edit delete Words (imitate database)

class WordsRepo (
    private val dao: WordsDao
) {

    fun getAllWords(): Flow<List<Word>> {
        return dao.getAllWords()
    }

    fun getCompletedWords(): Flow<List<Word>> {
        return dao.getCompletedWords()
    }

    fun getUnlearnedWords(): Flow<List<Word>> {
        return dao.getUnlearnedWords()
    }

    fun addWord(word: Word){
        dao.addWord(word)
    }

    fun getWord(id: Int): Word?{
        return dao.getWord(id)
    }

    fun updateWord(word: Word){
        dao.updateWord(word)
    }

    fun deleteWord(id: Int){
        dao.deleteWord(id)
    }
}