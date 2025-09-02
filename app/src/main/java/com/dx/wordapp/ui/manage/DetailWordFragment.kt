package com.dx.wordapp.ui.manage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dx.wordapp.R
import com.dx.wordapp.data.repo.WordsRepo
import com.dx.wordapp.databinding.FragmentDetailWordBinding


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

        val word = repo.getWord(args.wordId) ?: throw Exception("Word is Null")
        binding.run {
            tvTitle.setText(word.title)
            tvDefinition.setText(word.definition)
            tvSynonym.setText(word.synonym)
            tvDetails.setText(word.details)

//            btnDone.setOnClickListener{}
            btnEdit.setOnClickListener {
                val action = DetailWordFragmentDirections.actionDetailWordFragmentToEditWordFragment(it.id!!)
                findNavController().navigate(action)
            }
            btnDelete.setOnClickListener {
                repo.deleteWord(it.id!!)
            }
        }
    }


}