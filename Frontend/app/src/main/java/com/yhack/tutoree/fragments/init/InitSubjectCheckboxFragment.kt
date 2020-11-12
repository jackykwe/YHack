package com.yhack.tutoree.fragments.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.yhack.tutoree.R
import com.yhack.tutoree.databinding.FragmentInitSubjectCheckboxBinding

class InitSubjectCheckboxFragment : Fragment() {

    private lateinit var binding: FragmentInitSubjectCheckboxBinding
    private val args: InitSubjectCheckboxFragmentArgs by navArgs()

    private val subjectsChosen = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitSubjectCheckboxBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun toggleButton(button: Button) {
        val tag = button.tag as String
        if (tag in subjectsChosen) {
            subjectsChosen.remove(tag)
            button.backgroundTintList =
                resources.getColorStateList(R.color.mbtiUnselect, requireActivity().theme)
        } else {
            subjectsChosen.add(tag)
            button.backgroundTintList =
                resources.getColorStateList(R.color.mbtiSelected, requireActivity().theme)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val splitText = if (args.isTutor) "can you teach?" else "do you need help with?"
        binding.mainTextView.text = "What subjects\n$splitText"
        binding.apply {
            mathButton.setOnClickListener { toggleButton(it as Button) }
            englishButton.setOnClickListener { toggleButton(it as Button) }
            chineseButton.setOnClickListener { toggleButton(it as Button) }
            chemistryButton.setOnClickListener { toggleButton(it as Button) }
            physicsButton.setOnClickListener { toggleButton(it as Button) }
            biologyButton.setOnClickListener { toggleButton(it as Button) }
            historyButton.setOnClickListener { toggleButton(it as Button) }
            geographyButton.setOnClickListener { toggleButton(it as Button) }
            literatureButton.setOnClickListener { toggleButton(it as Button) }
        }
        binding.firstButton.apply {
            text = "Finish Setup"
            setOnClickListener {
                if (subjectsChosen.isEmpty()) {
                    Snackbar.make(
                        binding.root,
                        "Please choose at least 1 subject!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    findNavController().apply {
                        if (currentDestination?.id == R.id.initSubjectCheckboxFragment) {
                            navigate(
                                InitSubjectCheckboxFragmentDirections.actionInitSubjectCheckboxFragmentToInitExitFragment(
                                    isTutor = args.isTutor
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}