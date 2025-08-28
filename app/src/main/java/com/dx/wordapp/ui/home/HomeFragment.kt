package com.dx.wordapp.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import androidx.fragment.app.setFragmentResultListener
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.dx.wordapp.R
import com.dx.wordapp.databinding.FragmentHomeBinding
import com.dx.wordapp.ui.adapter.WordsAdapter

// HomeFragment (setupAdapter not done)

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: WordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

//    fun setupAdapter(){
//        adapter = WordsAdapter(emptyList()){
//            val action = HomeFragmentDirections.actionHomeFragmentToEditWordFragment(it.id!!)
//            findNavController().navigate(action)
//
//            setFragmentResultListener("manage_product"){_,_ ->
//                viewModel.getWords()
//            }
//        }
//        binding.rvWords.layoutManager = LinearLayoutManager(requireContext())
//        binding.rvWords.adapter = adapter
//    }
}