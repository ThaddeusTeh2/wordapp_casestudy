package com.dx.wordapp.ui.manage

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dx.wordapp.R
import com.dx.wordapp.databinding.FragmentBaseManageWordBinding

class AddWordFragment : BaseManageWordFragment() {
    private val viewModel: AddWordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding
    }
}