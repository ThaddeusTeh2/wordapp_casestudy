package com.dx.wordapp.ui.home

import android.app.Dialog
import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dx.wordapp.R
import com.dx.wordapp.databinding.DialogFilterBinding
import com.dx.wordapp.databinding.FragmentBaseHomeBinding
import com.dx.wordapp.ui.adapter.WordsAdapter
import kotlinx.coroutines.launch

class CompletedFragment : Fragment() {
    private val viewModel: CompletedViewModel by viewModels()
    private lateinit var binding: FragmentBaseHomeBinding
    private lateinit var adapter: WordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        setupNavigation()
        observeWords()


        // Standard: Navigate to Add screen when FAB is clicked.
        // Factory analogy: Create a new assembly when pressing the add control.
        binding.run {
            fabAdd.setOnClickListener {
                val action = CompletedFragmentDirections.actionCompletedFragmentToAddWordFragment()
                findNavController().navigate(action)
            }
            fabSort.setOnClickListener { showFilterDialogBox(id) }
        }

        // Standard: Refresh list when add/edit finishes in child fragments.
        // Factory analogy: Update conveyor when another station finishes processing.
        setFragmentResultListener("manage_word"){_,_ ->
            viewModel.getWords()
        }
    }

    /**
     * Standard: Wire toolbar and bottom navigation to the activity nav controller.
     * Factory analogy: Connect control panels to the main logistics network.
     */
    private fun setupNavigation() {
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.toolbar.setupWithNavController(navController)
    }

    /**
     * Standard: Observe words and update list and empty state.
     * Factory analogy: Watch the belt and toggle the "no production" sign.
     */
    fun observeWords(){
        lifecycleScope.launch {
            viewModel.words.collect{ words ->
                adapter.setWords(words)
                updateEmptyState(words.isEmpty())
            }
        }
    }

    /**
     * Standard: Show or hide empty-state container.
     * Factory analogy: Show the "factory closed" sign when nothing is produced.
     */
    private fun updateEmptyState(isEmpty: Boolean) {
        binding.llEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    /**
     * Standard: Initialize RecyclerView and item click navigation to Edit.
     * Factory analogy: Set up the conveyor and route items to the upgrade station.
     */
    fun initializeRecyclerView(){
        adapter = WordsAdapter(emptyList()){
            val action = CompletedFragmentDirections.actionCompletedFragmentToDetailWordFragment(it.id!!)
            findNavController().navigate(action)

            setFragmentResultListener("manage_word"){_,_ ->
                viewModel.getWords()
            }
        }
        binding.rvWords.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWords.adapter = adapter
    }

    // Refresh the page everytime you resume the page (to fix the ui not refreshing issue)
    override fun onResume() {
        super.onResume()
        viewModel.getWords()
    }

    // Show sort dialog box
    private fun showFilterDialogBox(wordId: Int) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogFilterBinding
            .inflate(layoutInflater, null, false)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.root.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        dialogBinding.run {
            rbSortByTitle.setOnClickListener {

            }
            rbSortByDate.setOnClickListener{
                viewModel.sortByDate()
            }

            rbAscending.setOnClickListener {  }
            rbDescending.setOnClickListener {  }

            btnCancel.setOnClickListener { dialog.dismiss() }
            btnApply.setOnClickListener {  }
        }

        dialog.show()
    }

}