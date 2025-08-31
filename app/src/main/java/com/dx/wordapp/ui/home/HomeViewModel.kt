package com.dx.wordapp.ui.home

import androidx.lifecycle.ViewModel
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Logistics Network Controller - Manages the flow of items in the factory
 * This is like the logistics network that controls how items move through the system
 * Handles item storage, retrieval, and network updates
 */
class HomeViewModel(
    private val repo : WordsRepo = WordsRepo.getInstance()
)  : ViewModel() {
    
    // Current items in the logistics network
    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words = _words.asStateFlow()

    init {
        getWords()
    }

    /**
     * Retrieve all items from the logistics network
     * Like requesting a full inventory of all items in the factory
     */
    fun getWords(){
        _words.value = repo.getAllWords()
    }
    
    // TODO: TEAMMATE - Add search functionality
    // Integration point: Filter items based on search query
    // This should search through the logistics network for matching items
    // Implementation: Add search method that filters items by title/definition
    
    // TODO: TEAMMATE - Add sorting functionality
    // Integration point: Sort items in the logistics network
    // This should sort items by title, date, or other criteria
    // Implementation: Add sort methods for different sorting options
    
    // TODO: TEAMMATE - Add filtering by completion status
    // Integration point: Filter items by completed/unlearned status
    // This should show only completed or unlearned items
    // Implementation: Add filter methods for completion status
}