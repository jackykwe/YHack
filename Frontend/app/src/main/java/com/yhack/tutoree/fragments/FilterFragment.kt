package com.yhack.tutoree.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.tutoree.R
import com.yhack.tutoree.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {

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
        binding.mainTextView.text = "Filter (Gender, Subject, etc.)"
        binding.firstButton.apply {
            text = "Submit Survey"
            setOnClickListener {
                findNavController().apply {
                    if (currentDestination?.id == R.id.filterFragment) {
                        navigate(FilterFragmentDirections.actionFilterFragmentToHomeFragment())
                    }
                }
            }
        }
    }
}