package com.dx.wordapp.ui.home

import androidx.lifecycle.ViewModel
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Standard: ViewModel holding the current list of words and exposing them as a StateFlow.
 * Provides a method to refresh from the repository.
 *
 * Factory analogy: Logistics controller that tracks the items on the main belt.
 */
class HomeViewModel(
    private val repo : WordsRepo = WordsRepo.getInstance()
)  : ViewModel() {

    // Standard: Backing state for the UI to observe.
    // Factory analogy: Live counter of items on the belt.
    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words = _words.asStateFlow()

    init {
        getWords()
    }

    /**
     * Standard: Load all words from the repository.
     * Factory analogy: Request a fresh snapshot of items from storage.
     */
    fun getWords(){
        _words.value = repo.getAllWords()
    }

    // Standard: TODO add search/filters; update _words based on criteria.
    // Factory analogy: Apply filter/sort modules to the main belt.
}