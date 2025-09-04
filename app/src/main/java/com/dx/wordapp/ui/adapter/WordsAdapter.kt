package com.dx.wordapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dx.wordapp.R
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.databinding.LayoutItemWordBinding

/**
 * Standard: RecyclerView adapter that binds Word items to list cards.
 * Factory analogy: Conveyor controller that presents items on the line.
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
     * Standard: Replace the current list and refresh the UI.
     * Factory analogy: Swap the items on the belt with a new batch.
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
     * Standard: Holds references to the item views and binds a Word.
     * Factory analogy: Display panel for each item moving on the belt.
     */
    inner class WordViewHolder(
        private val binding : LayoutItemWordBinding
    ):RecyclerView.ViewHolder(binding.root){
        /**
         * Standard: Populate the card views and wire click listener.
         * Factory analogy: Show item info and route to the next station when pressed.
         */
        fun bind(item:Word){
            binding.run {
                tvTitle.text = item.title
                tvDefinition.text = root.context.getString(
                    R.string.definition_yo,
                    item.definition
                )

                cvWord.setOnClickListener{
                    onClick(item)
                }
            }
        }
    }
}