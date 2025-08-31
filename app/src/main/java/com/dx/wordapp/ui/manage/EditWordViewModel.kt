package com.dx.wordapp.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Production Line Controller for modifying existing items
 * This manages the assembly machine that processes item modifications
 * Like upgrading an existing item in the logistics network
 */
class EditWordViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {
    
    // Current item being processed in the assembly machine
    private val _word = MutableStateFlow<Word?>(null)
    val word: StateFlow<Word?> = _word
    
    // Signal when production is complete (item successfully modified)
    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish
    
    // Error signal when production fails (like a broken assembly machine)
    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error
    
    /**
     * Load an item from the logistics network by its ID
     * Like requesting an item from the logistics network storage
     * @param wordId The unique identifier of the item to retrieve
     */
    fun loadWord(wordId: Int) {
        val word = repo.getWord(wordId)
        if (word != null) {
            _word.value = word
        } else {
            viewModelScope.launch {
                _error.emit("Item not found in logistics network")
            }
        }
    }
    
    /**
     * Process item modification in the assembly machine
     * Like upgrading an existing item with new properties
     * @param title New item name
     * @param definition New item description
     * @param synonym New item category
     * @param details New item specifications
     */
    fun updateWord(title: String, definition: String, synonym: String, details: String) {
        try {
            // Validate input materials (like checking if you have the required ingredients)
            require(title.isNotBlank()) { "Item name cannot be empty" }
            require(definition.isNotBlank()) { "Item description cannot be empty" }
            
            // Get the current item from the assembly machine
            val currentWord = _word.value
            if (currentWord != null) {
                // Create the upgraded item with new properties
                val updatedWord = currentWord.copy(
                    title = title,
                    definition = definition,
                    synonym = synonym,
                    details = details
                )
                
                // Send the upgraded item back to the logistics network
                repo.updateWord(updatedWord)
                
                // Signal that production is complete
                viewModelScope.launch {
                    _finish.emit(Unit)
                }
            } else {
                viewModelScope.launch {
                    _error.emit("No item loaded in assembly machine")
                }
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                _error.emit(e.message ?: "Assembly machine malfunction")
            }
        }
    }
}