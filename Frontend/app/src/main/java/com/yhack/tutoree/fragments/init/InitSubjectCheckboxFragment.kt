package com.yhack.tutoree.fragments.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.tutoree.R
import com.yhack.tutoree.databinding.FragmentInitSubjectCheckboxBinding

class InitSubjectCheckboxFragment : Fragment() {

    private lateinit var binding: FragmentInitSubjectCheckboxBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitSubjectCheckboxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text =
            "What subjects do you nede help with / can you teach? TODO SEPARATE" // TODO
        binding.firstButton.apply {
            text = "Finish Setup"
            setOnClickListener {
                findNavController().apply {
                    if (currentDestination?.id == R.id.initSubjectCheckboxFragment) {
                        navigate(InitSubjectCheckboxFragmentDirections.actionInitSubjectCheckboxFragmentToInitExitFragment())
                    }
                }
            }
        }
    }
}