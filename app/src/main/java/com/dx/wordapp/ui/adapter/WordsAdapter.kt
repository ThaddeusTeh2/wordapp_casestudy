package com.dx.wordapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dx.wordapp.R
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.databinding.LayoutItemWordBinding

/**
 * Conveyor Belt Controller - Manages the display of items on the production line
 * This is like a smart conveyor belt that shows items from the logistics network
 * Each item is displayed as a card on the production floor
 */
class WordsAdapter(
    private var word : List<Word>,
    private val onClick: (Word) -> Unit
):RecyclerView.Adapter<WordsAdapter.WordViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemWordBinding.inflate(layoutInflater, parent, false)
        return WordViewHolder(binding)
    }

    override fun getItemCount() = word.size

    /**
     * Update the items on the conveyor belt
     * Like replacing items on a conveyor belt with new ones
     * @param items The new items to display on the production line
     */
    fun setWords(items:List<Word>){
        this.word = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = word[position]
        holder.bind(item)
    }

    /**
     * Item Display Unit - Each item card on the production line
     * Like a display panel that shows information about an item on the conveyor belt
     */
    inner class WordViewHolder(
        private val binding : LayoutItemWordBinding
    ):RecyclerView.ViewHolder(binding.root){
        /**
         * Display item information on the card
         * Like showing item details on a display panel
         * @param item The item to display information for
         */
        fun bind(item:Word){
            binding.run {
                tvTitle.text = item.title
                tvDefinition.text = root.context.getString(
                    R.string.definition_yo,
                    item.definition
                )

                // Setup click handler for item selection
                cvWord.setOnClickListener{
                    onClick(item)
                }
            }
        }
    }
}