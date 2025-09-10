package com.dx.wordapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.dx.wordapp.R
import com.dx.wordapp.databinding.FragmentBaseHomeBinding
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.google.android.material.snackbar.Snackbar

class BaseHomeFragment : Fragment() {
    protected lateinit var binding: FragmentBaseHomeBinding

    private lateinit var searchBar: SearchBar
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBaseHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBar = view.findViewById(R.id.search)
        searchView = view.findViewById(R.id.search_view)

        // Link SearchView with SearchBar
        searchView.setupWithSearchBar(searchBar)


    }


    fun showError(msg:String){
        val snackbar = Snackbar.make(binding.root,msg, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red))
        snackbar.show()
    }

}