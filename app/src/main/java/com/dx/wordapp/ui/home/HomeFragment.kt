package com.dx.wordapp.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dx.wordapp.databinding.FragmentHomeBinding
import com.dx.wordapp.ui.adapter.WordsAdapter
import kotlinx.coroutines.launch

/**
 * Main Production Floor - The central hub for viewing and managing all items
 * This is like the main factory floor where you can see all your production lines
 * and manage the logistics network of items
 */
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupProductionLine()
        observeLogisticsNetwork()
        
        // Setup the item creation button (like building a new assembly machine)
        binding.fabAdd.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToAddWordFragment()
            findNavController().navigate(action)
        }
        
        // Listen for production updates from other assembly machines
        setFragmentResultListener("manage_word"){_,_ ->
            viewModel.getWords()
        }
    }

    /**
     * Monitor the logistics network for item updates
     * Like watching the main bus for new items being added to the network
     */
    fun observeLogisticsNetwork(){
        lifecycleScope.launch {
            viewModel.words.collect{
                adapter.setWords(it)
            }
        }
    }

    /**
     * Setup the production line conveyor belt (RecyclerView)
     * Like setting up a conveyor belt to display items from the logistics network
     */
    fun setupProductionLine(){
        adapter = WordsAdapter(emptyList()){
            // Navigate to the item modification assembly machine
            val action = HomeFragmentDirections.actionHomeFragmentToEditWordFragment(it.id!!)
            findNavController().navigate(action)

            // Listen for updates from the modification assembly machine
            setFragmentResultListener("manage_product"){_,_ ->
                viewModel.getWords()
            }
        }
        binding.rvWords.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWords.adapter = adapter
    }
    
    // TODO: TEAMMATE - Add search functionality integration
    // Integration point: Connect search bar to filter items in the logistics network
    // This should filter the displayed items based on search query
    // Implementation: Add search bar in toolbar and connect to HomeViewModel search methods
    
    // TODO: TEAMMATE - Add filter/sort functionality integration  
    // Integration point: Connect filter dialog to sort items in the logistics network
    // This should sort items by title, date, or other criteria
    // Implementation: Add filter dialog and connect to HomeViewModel sort methods
    
    // TODO: TEAMMATE - Add empty state handling
    // Integration point: Show empty state when no items in logistics network
    // This should display a message when the production floor is empty
    // Implementation: Add empty state layout and logic
    
    // TODO: TEAMMATE - Add item detail navigation
    // Integration point: Navigate to item detail view when item is clicked
    // This should show detailed information about the selected item
    // Implementation: Add navigation to WordDetailFragment
    
    // TODO: TEAMMATE - Add tab navigation for completed/unlearned items
    // Integration point: Switch between different item categories
    // This should filter items based on completion status
    // Implementation: Add bottom navigation integration with CompletedWordsFragment
}