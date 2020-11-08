package com.yhack.eetutor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.yhack.eetutor.R
import com.yhack.eetutor.databinding.FragmentFilterBinding

class FullProfileFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding

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
                        navigate(FullProfileFragmentDirections.actionFullProfileFragmentToChatFragment())
                    }
                }
            }
        }
    }
}