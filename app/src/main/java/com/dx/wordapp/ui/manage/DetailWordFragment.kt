package com.dx.wordapp.ui.manage

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dx.wordapp.R
import com.dx.wordapp.data.repo.WordsRepo
import com.dx.wordapp.databinding.FragmentDetailWordBinding
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.setFragmentResult
import com.dx.wordapp.data.model.Word
import com.dx.wordapp.databinding.DialogConfirmationBinding
import com.google.android.material.chip.Chip


class DetailWordFragment: Fragment() {
    protected val repo = WordsRepo.getInstance()
    private lateinit var binding : FragmentDetailWordBinding
    private val args : DetailWordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailWordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
    }

    // Binding Block
    private fun updateUI(){
        val word = repo.getWord(args.wordId) ?: throw Exception("Word is Null")
        syn()
        binding.run {
            tvTitle.setText(word.title)
            tvDefinition.setText(word.definition)
            tvDetails.setText(word.details)
            runBtnDoneBinding(word)
            btnEdit.setOnClickListener {
                val action = DetailWordFragmentDirections
                    .actionDetailWordFragmentToEditWordFragment(args.wordId)
                findNavController().navigate(action)
            }
            btnDelete.setOnClickListener { showDeleteDialogBox(args.wordId) }
            btnBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    fun runBtnDoneBinding(word:Word){
        binding.run {
            btnDone.icon = if (!word.isCompleted) {
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_done)
            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_undone)
            }
            btnDone.setOnClickListener{ changeWordCompleted()}
        }
    }

    fun syn() {
        val word = repo.getWord(args.wordId) ?: return
        binding.chipGroup.removeAllViews() // clear old chips

        word.synonym.split(",").map { it.trim() }.forEach { synonym ->
            if (synonym != ""){
                val chip = Chip(requireContext()).apply {
                    text = synonym
                    isClickable = false
                    isCheckable = false
                }
                binding.chipGroup.addView(chip)
            }
        }
    }


    // Update isCompleted to true
    fun changeWordCompleted(){
        val word = repo.getWord(args.wordId) ?: throw Exception("Word is Null")
        if (!word.isCompleted){
            repo.updateWord(word.copy(isCompleted = true))
        }else{
            repo.updateWord(word.copy(isCompleted = false))
        }
        // Notify both lists
        setFragmentResult("manage_word", Bundle()) // Home
        setFragmentResult("manage_completed_word", Bundle()) // Completed

        findNavController().popBackStack()
    }


    //    Dialog popup
    private fun showDeleteDialogBox(wordId: Int) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogConfirmationBinding
            .inflate(layoutInflater, null, false)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.root.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnConfirm.setOnClickListener {
            repo.deleteWord(wordId)
            dialog.dismiss()
            setFragmentResult("manage_word",Bundle())
            findNavController().popBackStack()
        }
        dialog.show()
    }
}