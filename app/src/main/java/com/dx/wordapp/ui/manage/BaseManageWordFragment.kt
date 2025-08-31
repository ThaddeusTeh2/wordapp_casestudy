package com.dx.wordapp.ui.manage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.dx.wordapp.R
import com.dx.wordapp.data.repo.WordsRepo
import com.dx.wordapp.databinding.FragmentBaseManageWordBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Base Assembly Machine - The foundation for all item production and modification
 * This is like the basic assembly machine that can be configured for different recipes
 * All other assembly machines (AddWord, EditWord) extend this base machine
 */
open class BaseManageWordFragment : Fragment() {
    // Access to the logistics network for item storage and retrieval
    protected val repo = WordsRepo.getInstance()
    protected lateinit var binding: FragmentBaseManageWordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the assembly machine interface layout
        binding = FragmentBaseManageWordBinding.inflate(inflater,container,false)
        return binding.root
    }

    /**
     * Display error messages when the assembly machine malfunctions
     * Like showing error signals when production fails
     * @param msg The error message to display
     */
    fun showError(msg:String){
        val snackbar = Snackbar.make(binding.root,msg, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red))
        snackbar.show()
    }
    
    // TODO: TEAMMATE - Add validation methods for input fields
    // Integration point: Add methods to validate assembly machine inputs
    // This should check if all required materials are present before production
    
    // TODO: TEAMMATE - Add methods for handling different production modes
    // Integration point: Add methods for different assembly machine configurations
    // This should handle different production recipes (add vs edit modes)
}