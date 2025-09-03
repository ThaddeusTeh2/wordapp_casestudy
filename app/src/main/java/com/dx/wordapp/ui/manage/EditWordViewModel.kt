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
 * Standard: ViewModel for editing an existing word. Loads selected word and updates it.
 * Factory analogy: Controller for an upgrade station managing item modifications.
 */
class EditWordViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {

    // Standard: Holds the selected word to edit.
    // Factory analogy: The item currently on the machine.
    private val _word = MutableStateFlow<Word?>(null)
    val word: StateFlow<Word?> = _word

    // Standard: Emits when update completes successfully.
    // Factory analogy: Green light when the job finishes.
    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish

    // Standard: Emits errors to be shown in the UI.
    // Factory analogy: Fault signal from the station.
    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    /**
     * Standard: Load a word by id from the repository.
     * Factory analogy: Request an item from storage to process.
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
     * Standard: Validate inputs and update the word.
     * Factory analogy: Check materials and run the upgrade recipe.
     */
    fun updateWord(title: String, definition: String, synonym: String, details: String) {
        try {
            validateInputMaterials(title, definition)
            processItemModification(title, definition, synonym, details)
        } catch (e: Exception) {
            handleAssemblyError(e)
        }
    }

    /**
     * Standard: Ensure required fields are provided.
     * Factory analogy: Verify ingredients before production starts.
     */
    private fun validateInputMaterials(title: String, definition: String) {
        require(title.isNotBlank()) { "Item name cannot be empty" }
        require(definition.isNotBlank()) { "Item description cannot be empty" }
    }

    /**
     * Standard: Create updated model and persist it.
     * Factory analogy: Run the upgrade on the item and send it back to storage.
     */
    private fun processItemModification(title: String, definition: String, synonym: String, details: String) {
        val currentWord = _word.value
        if (currentWord != null) {
            val updatedWord = createUpgradedItem(currentWord, title, definition, synonym, details)
            sendItemToLogisticsNetwork(updatedWord)
            signalProductionComplete()
        } else {
            signalAssemblyError("No item loaded in assembly machine")
        }
    }

    /**
     * Standard: Build a new instance with edited fields.
     * Factory analogy: Produce the upgraded item on the machine.
     */
    private fun createUpgradedItem(
        currentWord: Word,
        title: String,
        definition: String,
        synonym: String,
        details: String
    ): Word {
        return currentWord.copy(
            title = title,
            definition = definition,
            synonym = synonym,
            details = details
        )
    }

    /**
     * Standard: Persist updated item in the repository.
     * Factory analogy: Return finished goods to logistics.
     */
    private fun sendItemToLogisticsNetwork(updatedWord: Word) {
        repo.updateWord(updatedWord)
    }

    /**
     * Standard: Notify UI that update finished.
     * Factory analogy: Turn on the green completion light.
     */
    private fun signalProductionComplete() {
        viewModelScope.launch {
            _finish.emit(Unit)
        }
    }

    /**
     * Standard: Convert exceptions into user-visible errors.
     * Factory analogy: Raise a fault signal with a message.
     */
    private fun handleAssemblyError(exception: Exception) {
        signalAssemblyError(exception.message ?: "Assembly machine malfunction")
    }

    /**
     * Standard: Emit an error message for the UI.
     * Factory analogy: Flash the warning indicator with the fault text.
     */
    private fun signalAssemblyError(errorMessage: String) {
        viewModelScope.launch {
            _error.emit(errorMessage)
        }
    }
}