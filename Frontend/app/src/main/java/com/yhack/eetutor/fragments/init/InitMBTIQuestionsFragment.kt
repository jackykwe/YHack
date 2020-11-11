package com.yhack.eetutor.fragments.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.yhack.eetutor.R
import com.yhack.eetutor.databinding.FragmentInitMbtiQuestionsBinding

class InitMBTIQuestionsFragment : Fragment() {

    private lateinit var binding: FragmentInitMbtiQuestionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitMbtiQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Personality Test"
        binding.firstButton.apply {
            text = "Submit"
            setOnClickListener {
                findNavController().apply {
                    if (currentDestination?.id == R.id.initMBTIQuestionsFragment) {
                        navigate(InitMBTIQuestionsFragmentDirections.actionInitMBTIQuestionsFragmentToInitMBTIResultFragment())
                    }
                }
            }
        }
    }
}