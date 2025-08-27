package com.dx.wordapp.data.repo

import com.dx.wordapp.data.model.Word

class WordsRepo private constructor() {
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

    fun deleteWord(id:Int){
        items.remove(id)
    }

    companion object{
        private var instance: WordsRepo? = null

        fun getInstance():WordsRepo{
            if (instance == null){
                instance = WordsRepo()
            }
            return instance!!
        }
    }

}