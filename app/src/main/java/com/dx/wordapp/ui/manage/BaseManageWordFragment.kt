package com.dx.wordapp.ui.manage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dx.wordapp.R
import com.dx.wordapp.data.repo.WordsRepo
import com.dx.wordapp.databinding.FragmentBaseManageWordBinding

open class BaseManageWordFragment : Fragment() {
    protected val repo = WordsRepo.getInstance()
    protected lateinit var binding: FragmentBaseManageWordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBaseManageWordBinding.inflate(inflater,container,false)
        return binding.root
    }

    // navigationBack function here (waiting for navGraph)
}