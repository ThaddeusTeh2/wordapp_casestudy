package com.dx.wordapp.ui.home

import androidx.lifecycle.ViewModel
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

    // manage the list displayed in the search view, keeping it independent from the main screen's list.
    private val _searchResults = MutableStateFlow<List<Word>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    init {
        getWords()
    }

    /**
     * Standard: Load all words from the repository.
     * Factory analogy: Request a fresh snapshot of items from storage.
     */
    fun getWords(){
        val unlearnedWords = repo.getUnlearnedWords()
        _words.value = unlearnedWords
        _searchResults.value = unlearnedWords

    }

    // Standard: TODO add search/filters; update _words based on criteria.
    // Factory analogy: Apply filter/sort modules to the main belt.
    // Replace your existing sort functions

    fun sortByTitle() {
        _words.update { it.sortedBy { word -> word.title } }
    }

    fun sortByDate() {
        _words.update { it.sortedBy { word -> word.date } }
    }

    fun sortByAscending() {
        _words.update { it.sortedBy { word -> word.date } }
    }

    fun sortByDescending() {
        _words.update { it.sortedByDescending { word -> word.date } }
    }

    // filters the master word list based on a user's query and updates the _searchResults flow
    fun searchWords(query: String) {
        if (query.isBlank()) {
            _searchResults.value = _words.value
            return
        }
        _searchResults.value = _words.value.filter { word ->
            word.title.contains(query, ignoreCase = true)
        }
    }
}