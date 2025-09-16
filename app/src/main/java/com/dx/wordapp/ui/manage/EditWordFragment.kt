package com.dx.wordapp.ui.manage

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dx.wordapp.R
import kotlinx.coroutines.launch

/**
 * Standard: Screen for editing an existing word. Pre-fills the form and saves updates.
 * Factory analogy: Upgrade station that modifies an existing item on the line.
 */
class EditWordFragment : BaseManageWordFragment() {
    private val viewModel: EditWordViewModel by viewModels{
        EditWordViewModel.Factory
    }
    private val args: EditWordFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Standard: Load the word by id and populate the form.
        // Factory analogy: Fetch the item from storage onto the machine.
        viewModel.loadWord(args.wordId)

        setupUI()
        observeViewModel()
    }

    /**
     * Standard: Configure toolbar, button labels and submit handler.
     * Factory analogy: Set the machine controls and start the upgrade.
     */
    private fun setupUI() {
        binding.run {
            setupBackNavigation()
            setEditTitle()
            setupSubmitHandler()
        }
    }

    /**
     * Standard: Configure toolbar back navigation.
     * Factory analogy: Exit the station.
     */
    private fun setupBackNavigation() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     * Standard: Indicate this is edit mode.
     * Factory analogy: Machine set to upgrade mode.
     */
    private fun setEditTitle() {
        binding.toolbarTitle.text = getString(R.string.manage_word_title, "Modify")
    }

    /**
     * Standard: Gather inputs and request ViewModel to save.
     * Factory analogy: Feed materials and run the recipe.
     */
    private fun setupSubmitHandler() {
        binding.mbSubmit.text = getString(R.string.update)
        binding.mbSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val definition = binding.etDefinition.text.toString()
            val synonym = binding.etSynonym.text.toString()
            val details = binding.etDetails.text.toString()
            viewModel.updateWord(title, definition, synonym, details)
        }
    }

    /**
     * Standard: Observe word load, completion, and error events.
     * Factory analogy: Watch the station signals for success or faults.
     */
    private fun observeViewModel() {
        observeWordState()
        observeCompletion()
        observeErrors()
    }

    /**
     * Standard: Observe the current word and populate the form.
     * Factory analogy: Place the item on the machine for processing.
     */
    private fun observeWordState() {
        lifecycleScope.launch {
            viewModel.word.collect { word ->
                word?.let { populateForm(it) }
            }
        }
    }

    /**
     * Standard: Observe completion and navigate back.
     * Factory analogy: Green light and send item to the next step.
     */
    private fun observeCompletion() {
        lifecycleScope.launch {
            viewModel.finish.collect {
                setFragmentResult("manage_word", Bundle())
                findNavController().popBackStack()
            }
        }
    }

    /**
     * Standard: Observe errors and show message.
     * Factory analogy: Fault indicator with reason.
     */
    private fun observeErrors() {
        lifecycleScope.launch {
            viewModel.error.collect { errorMessage ->
                showError(errorMessage)
            }
        }
    }

    /**
     * Standard: Fill the form with the selected word's values.
     * Factory analogy: Place the item on the machine for processing.
     */
    private fun populateForm(word: com.dx.wordapp.data.model.Word) {
        binding.run {
            etTitle.setText(word.title)
            etDefinition.setText(word.definition)
            etSynonym.setText(word.synonym)
            etDetails.setText(word.details)
        }
    }
}