package com.dx.wordapp.ui.home

import androidx.lifecycle.ViewModel
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// view model for HomeFragment

class HomeViewModel(
    private val repo : WordsRepo = WordsRepo.getInstance()
)  : ViewModel() {
    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words = _words.asStateFlow()

    init {
        getWords()
    }

    fun getWords(){
        _words.value = repo.getAllWords()
    }
}