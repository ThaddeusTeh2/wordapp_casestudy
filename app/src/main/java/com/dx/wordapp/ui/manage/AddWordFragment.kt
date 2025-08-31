package com.dx.wordapp.ui.manage

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dx.wordapp.R
import kotlinx.coroutines.launch

class AddWordFragment : BaseManageWordFragment() {
    private val viewModel: AddWordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            // for the toolbar title
            toolbarTitle.text = getString(R.string.manage_word_title, "Add New")
            mbSubmit.text = getString(R.string.add)
            mbSubmit.setOnClickListener {
                val title = etTitle.text.toString()
                val definition = etDefinition.text.toString()
                val synonym = etSynonym.text.toString()
                val details = etDetails.text.toString()
                viewModel.addWord(title = title,definition = definition, synonym = synonym, details = details)
            }
        }
        finish()
        error()
    }

    fun finish(){
        lifecycleScope.launch {
            viewModel.finish.collect{
                setFragmentResult("manage_word",Bundle())
                findNavController().popBackStack()
            }
        }
    }

    fun error(){
        lifecycleScope.launch {
            viewModel.error.collect{
                showError(it)
            }
        }
    }
}