package com.yhack.eetutor.fragments.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.yhack.eetutor.R
import com.yhack.eetutor.databinding.FragmentInitMbtiResultBinding

class InitMBTIResultFragment : Fragment() {

    private lateinit var binding: FragmentInitMbtiResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitMbtiResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Your MBTI personality type is..."
        binding.firstButton.apply {
            text = "OK"
            setOnClickListener {
                findNavController().apply {
                    if (currentDestination?.id == R.id.initMBTIResultFragment) {
                        navigate(InitMBTIResultFragmentDirections.actionInitMBTIResultFragmentToInitSubjectCheckboxFragment())
                    }
                }
            }
        }
    }
}