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
 * Assembly Machine Interface for item modification
 * This is the control panel for the assembly machine that upgrades existing items
 * Extends the base assembly machine to reuse the production line layout
 */
class EditWordFragment : BaseManageWordFragment() {
    private val viewModel: EditWordViewModel by viewModels()
    private val args: EditWordFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Load the item to be modified using the logistics network ID
        viewModel.loadWord(args.wordId)
        
        setupAssemblyInterface()
        observeProductionSignals()
    }
    
    /**
     * Configure the assembly machine control panel
     * Like setting up the interface for an assembly machine
     */
    private fun setupAssemblyInterface() {
        binding.run {
            // Set the toolbar icon back navigation
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            // Set the assembly machine name for modification mode
            toolbarTitle.text = getString(R.string.manage_word_title, "Modify")
            
            // Set the production button text for upgrading items
            mbSubmit.text = getString(R.string.update)
            
            // Setup the production button to start the modification process
            mbSubmit.setOnClickListener {
                val title = etTitle.text.toString()
                val definition = etDefinition.text.toString()
                val synonym = etSynonym.text.toString()
                val details = etDetails.text.toString()
                
                viewModel.updateWord(title, definition, synonym, details)
            }
        }
    }
    
    /**
     * Monitor the assembly machine status signals
     * Like watching the production line for completion or errors
     */
    private fun observeProductionSignals() {
        // Monitor the current item in the assembly machine
        lifecycleScope.launch {
            viewModel.word.collect { word ->
                word?.let { loadItemIntoAssemblyMachine(it) }
            }
        }

        // Monitor when production is complete
        lifecycleScope.launch {
            viewModel.finish.collect {
                setFragmentResult("manage_word", Bundle())
                findNavController().popBackStack()
            }
        }
        
        // Monitor for assembly machine errors
        lifecycleScope.launch {
            viewModel.error.collect { errorMessage ->
                showError(errorMessage)
            }
        }
    }
    
    /**
     * Load item data into the assembly machine interface
     * Like placing an item into an assembly machine for modification
     * @param word The item to load into the assembly machine
     */
    private fun loadItemIntoAssemblyMachine(word: com.dx.wordapp.data.model.Word) {
        binding.run {
            etTitle.setText(word.title)
            etDefinition.setText(word.definition)
            etSynonym.setText(word.synonym)
            etDetails.setText(word.details)
        }
    }
}