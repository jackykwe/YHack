package com.yhack.tutoree.fragments.init.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.tutoree.R
import com.yhack.tutoree.databinding.FragmentInitTutorInfoBinding

class InitTutorInfoFragment : Fragment() {

    private lateinit var binding: FragmentInitTutorInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitTutorInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Personal Information"
        binding.firstButton.apply {
            text = "Upload your documents"
            setOnClickListener {
                findNavController().apply {
                    if (currentDestination?.id == R.id.initTutorInfoFragment) {
                        navigate(InitTutorInfoFragmentDirections.actionInitTutorInfoFragmentToInitTutorVerifyFragment())
                    }
                }
            }
        }
    }
}