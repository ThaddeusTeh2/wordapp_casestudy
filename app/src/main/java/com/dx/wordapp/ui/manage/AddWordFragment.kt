package com.dx.wordapp.ui.manage

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dx.wordapp.R
import kotlinx.coroutines.launch

/**
 * Standard: Screen for adding new words. Handles form input and validation.
 * Factory analogy: Assembly station that creates new items from raw materials.
 */
class AddWordFragment : BaseManageWordFragment() {
    private val viewModel: AddWordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    /**
     * Standard: Configure toolbar, form labels and submit handler.
     * Factory analogy: Set up the assembly machine controls.
     */
    private fun setupUI() {
        binding.run {
            // Standard: Back navigation via toolbar.
            // Factory analogy: Exit the station.
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            // Standard: Indicate this is add mode.
            // Factory analogy: Machine set to creation mode.
            toolbarTitle.text = getString(R.string.manage_word_title, "Add New")
            mbSubmit.text = getString(R.string.add)
            mbSubmit.setOnClickListener {
                val title = etTitle.text.toString()
                val definition = etDefinition.text.toString()
                val synonym = etSynonym.text.toString()
                val details = etDetails.text.toString()
                viewModel.addWord(title = title, definition = definition, synonym = synonym, details = details)
            }
        }
    }

    /**
     * Standard: Observe completion and error events from ViewModel.
     * Factory analogy: Watch the station signals for success or faults.
     */
    private fun observeViewModel(){
        // Standard: Monitor when word creation completes.
        // Factory analogy: Green light when production finishes.
        lifecycleScope.launch {
            viewModel.finish.collect {
                setFragmentResult("manage_word", Bundle())
                findNavController().popBackStack()
            }
        }

        // Standard: Monitor for validation or processing errors.
        // Factory analogy: Fault indicator with reason.
        lifecycleScope.launch {
            viewModel.error.collect { errorMessage ->
                showError(errorMessage)
            }
        }
    }
}