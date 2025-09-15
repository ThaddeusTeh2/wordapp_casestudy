package com.dx.wordapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dx.wordapp.MyApp
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Standard: ViewModel holding the current list of words and exposing them as a StateFlow.
 * Provides a method to refresh from the repository.
 *
 * Factory analogy: Logistics controller that tracks the items on the main belt.
 */
class CompletedViewModel(
    private val repo : WordsRepo
)  : ViewModel() {

    // Standard: Backing state for the UI to observe.
    // Factory analogy: Live counter of items on the belt.
    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words = _words.asStateFlow()

    // manage the list displayed in the search view, keeping it independent from the main screen's list.
    private val _searchResults = MutableStateFlow<List<Word>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private var sortType = SortType.DATE
    private var sortOrder = SortOrder.ASCENDING

    init {
        getWords()
    }

    /**
     * Standard: Load all words from the repository.
     * Factory analogy: Request a fresh snapshot of items from storage.
     */
    fun getWords() {
        viewModelScope.launch {
            repo.getCompletedWords() // Flow<List<Word>>
                .collect { completed ->
                    val sorted = applySorting(completed)
                    _words.value = sorted
                    _searchResults.value = sorted
                }
        }
    }

    // Takes in two fields and pass it down to [applySorting()]
    fun setSortCompleted(type: SortType,order: SortOrder){
        sortType = type
        sortOrder = order
        applySortingCompleted()
    }

    fun searchWords(query: String) {
        if (query.isBlank()) {
            _searchResults.value = _words.value
            return
        }
        _searchResults.value = _words.value.filter { word ->
            word.title.contains(query, ignoreCase = true)
        }
    }

    // applying the sorted words into the state
    private fun applySortingCompleted() {
        val sorted = applySorting(_words.value)
        _words.value = sorted
    }

    // sorting function
    private fun applySorting(list: List<Word>): List<Word> {
        return when (sortType) {
            SortType.TITLE ->
                if (sortOrder == SortOrder.ASCENDING) list.sortedBy { it.title }
                else list.sortedByDescending { it.title }

            SortType.DATE ->
                if (sortOrder == SortOrder.ASCENDING) list.sortedBy { it.date }
                else list.sortedByDescending { it.date }
        }
    }

    // types and orders enum for easier changing
    enum class SortType{ TITLE,DATE }
    enum class SortOrder{ ASCENDING,DESCENDING }

    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Get the dependency in your factory
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                CompletedViewModel(
                    repo = myRepository,
                )
            }
        }
    }
}