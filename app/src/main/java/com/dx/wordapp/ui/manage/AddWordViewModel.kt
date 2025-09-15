package com.dx.wordapp.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * Standard: ViewModel for adding new words. Handles validation and persistence.
 * Factory analogy: Controller for an assembly station creating new items.
 */
class AddWordViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
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
            repoAdd(title,definition,synonym,details)

            viewModelScope.launch { 
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
}