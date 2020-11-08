package com.yhack.eetutor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yhack.eetutor.databinding.FragmentTemplateBinding

// This template doesn't serve any purpose apart from provide some starting code for new fragments
class TemplateFragment : Fragment() {

    private lateinit var binding: FragmentTemplateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTemplateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "TemplateFragment - this is as simple as it gets!"
        // Do the rest of fragment preparation here.
    }
}