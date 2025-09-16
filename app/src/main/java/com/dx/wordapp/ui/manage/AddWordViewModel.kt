package com.dx.wordapp.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dx.wordapp.MyApp
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * Standard: ViewModel for adding new words. Handles validation and persistence.
 * Factory analogy: Controller for an assembly station creating new items.
 */
class AddWordViewModel(
    private val repo: WordsRepo
) : ViewModel() {
    
    // Standard: Emits when word creation completes successfully.
    // Factory analogy: Green light when production finishes.
    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish

    // Standard: Emits errors to be shown in the UI.
    // Factory analogy: Fault signal from the station.
    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    /**
     * Standard: Validate inputs and create a new word in the repository.
     * Factory analogy: Check materials and run the creation recipe.
     */
    fun addWord(title: String, definition: String, synonym: String, details: String) {
        try {
            require(title.isNotBlank()) { "Title cannot be blank" }
            require(definition.isNotBlank()) { "Definition cannot be blank" }

            viewModelScope.launch (Dispatchers.IO) {
                repoAdd(title,definition,synonym,details)
                    _finish.emit(Unit)
            }
        } catch (e: Exception) {
            viewModelScope.launch { 
                _error.emit(e.message.toString()) 
            }
        }
    }

    private fun repoAdd(title:String, definition: String, synonym: String, details: String){
        val word = Word(
            title = title,
            definition = definition,
            synonym = synonym,
            details = details
        )
        repo.addWord(word)
    }

    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Get the dependency in your factory
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                AddWordViewModel(repo = myRepository)
            }
        }
    }
}