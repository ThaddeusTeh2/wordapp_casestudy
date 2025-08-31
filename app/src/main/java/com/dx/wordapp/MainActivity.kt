package com.dx.wordapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dx.wordapp.databinding.ActivityMainBinding

/**
 * Main Hub - The central control center that manages all navigation and UI components
 * Think of this as the main bus that connects all the different production lines (fragments)
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupWindowInsets()
        setupNavigation()
        setupToolbar()
    }
    
    /**
     * Configure the system boundaries for edge-to-edge display
     * Like setting up the map boundaries in a factory
     */
    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    
    /**
     * Setup the logistics network - connects all the production lines (fragments)
     * This is like setting up the main bus that carries items between different areas
     */
    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        // Connect the bottom navigation to the main logistics network
        binding.navView.setupWithNavController(navController)
        
        // Connect the toolbar to the navigation system
        binding.toolbar.setupWithNavController(navController)
    }
    
    /**
     * Setup the control panel with search and filter operations
     * Like setting up the logistics network controls for item routing
     */
    private fun setupToolbar() {
        binding.toolbar.inflateMenu(R.menu.toolbar_menu)
        
        // Setup the control panel buttons for item management
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> {
                    // TODO: TEAMMATE - Implement search functionality
                    // This should open a search interface to filter items in the inventory
                    // Integration point: Connect to HomeViewModel search methods
                    true
                }
                R.id.action_filter -> {
                    // TODO: TEAMMATE - Implement filter dialog
                    // This should show sorting options like logistics network filters
                    // Integration point: Connect to HomeViewModel filter methods
                    true
                }
                else -> false
            }
        }
    }
}