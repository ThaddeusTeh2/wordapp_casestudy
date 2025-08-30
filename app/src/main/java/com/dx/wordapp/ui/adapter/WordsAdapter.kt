package com.dx.wordapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dx.wordapp.R
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.databinding.LayoutItemWordBinding

// Main class to handle recyclerViews (main page data showing)

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

    fun setWords(items:List<Word>){
        this.word = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = word[position]
        holder.bind(item)
    }

    inner class WordViewHolder(
        private val binding : LayoutItemWordBinding
    ):RecyclerView.ViewHolder(binding.root){
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