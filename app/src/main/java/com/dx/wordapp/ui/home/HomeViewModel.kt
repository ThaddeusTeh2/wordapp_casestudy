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

    private var sortType = SortType.DATE
    private var sortOrder = SortOrder.ASCENDING

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

    // Takes in two fields and pass it down to [applySorting()]
    fun setSort(type: SortType,order: SortOrder){
        sortType = type
        sortOrder = order
        applySorting()
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

    // To check what type is passed and sort accordingly
    private fun applySorting() {
        val list = repo.getUnlearnedWords()
        val sorted = when (sortType) {
            SortType.TITLE ->
                if (sortOrder == SortOrder.ASCENDING) list.sortedBy { it.title }
                else list.sortedByDescending { it.title }

            SortType.DATE ->
                if (sortOrder == SortOrder.ASCENDING) list.sortedBy { it.date }
                else list.sortedByDescending { it.date }
        }
        _words.value = sorted
    }


    // types and orders enum for easier changing
    enum class SortType{ TITLE,DATE }
    enum class SortOrder{ ASCENDING,DESCENDING }

}