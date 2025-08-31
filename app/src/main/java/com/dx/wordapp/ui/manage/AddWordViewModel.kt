package com.dx.wordapp.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class AddWordViewModel (
    private val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {
    private val _finish = MutableSharedFlow<Unit>()
    val finish : SharedFlow<Unit> = _finish

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    fun addWord(title:String,definition:String,synonym:String,details:String){
        try {
            require(title.isNotBlank()) {"Title cannot be blank"}
            require(definition.isNotBlank()) {"Definition cannot be blank"}
            val word = Word(title = title, definition = definition, synonym = synonym, details = details)
            repo.addWord(word)
            viewModelScope.launch { _finish.emit(Unit)}
        }catch (e:Exception){
            viewModelScope.launch { _error.emit(e.message.toString()) }
        }
    }
}