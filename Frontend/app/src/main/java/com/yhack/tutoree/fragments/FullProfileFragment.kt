package com.yhack.tutoree.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yhack.tutoree.R
import com.yhack.tutoree.databinding.FragmentFilterBinding

class FullProfileFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding
    private val args: FullProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Full profile of a person"
        binding.firstButton.apply {
            text = "Chat (if enabled)"
            setOnClickListener {
                findNavController().run {
                    if (currentDestination?.id == R.id.fullProfileFragment) {
                        navigate(
                            FullProfileFragmentDirections.actionFullProfileFragmentToChatFragment(
                                isTutor = args.isTutor
                            )
                        )
                    }
                }
            }
        }
    }
}