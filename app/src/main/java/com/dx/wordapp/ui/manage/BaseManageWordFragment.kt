package com.dx.wordapp.ui.manage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.dx.wordapp.R
import com.dx.wordapp.databinding.FragmentBaseManageWordBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Standard: Base fragment for shared manage-word UI (form fields, toolbar, submit button).
 * Provides helpers (e.g., error display) and shared binding setup.
 *
 * Factory analogy: Base assembly machine that other stations (add/edit) extend.
 */
open class BaseManageWordFragment : Fragment() {
    // Standard: Shared repository instance.
    // Factory analogy: Shared logistics network access.
    protected lateinit var binding: FragmentBaseManageWordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Standard: Inflate shared layout with form fields.
        // Factory analogy: Prepare the base assembly machine panel.
        binding = FragmentBaseManageWordBinding.inflate(inflater,container,false)
        return binding.root
    }

    /**
     * Standard: Show a prominent error via Snackbar.
     * Factory analogy: Flash a warning light on the machine.
     */
    fun showError(msg:String){
        val snackbar = Snackbar.make(binding.root,msg, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red))
        snackbar.show()
    }
}