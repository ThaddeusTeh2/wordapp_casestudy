package com.dx.wordapp.data.repo

import com.dx.wordapp.data.model.Word

// Main class to get getAll add edit delete Words (imitate database)

class WordsRepoOld private constructor() {
    val items : MutableMap<Int,Word> = mutableMapOf()
    var counter = 0

    fun addWord(word: Word){
        counter++
        items[counter] = word.copy(id=counter)
    }

    fun getWord(id:Int) = items[id]

    fun updateWord(word: Word){
        items[word.id!!] = word
    }

    fun getAllWords() = items.values.toList()

    fun getCompletedWords() = items.values.filter { it.isCompleted }.toList()

    fun getUnlearnedWords() = items.values.filter { !it.isCompleted }.toList()


    fun deleteWord(id:Int){
        items.remove(id)
    }

    companion object{
        private var instance: WordsRepoOld? = null

        fun getInstance():WordsRepoOld{
            if (instance == null){
                instance = WordsRepoOld()
            }
            return instance!!
        }
    }

}