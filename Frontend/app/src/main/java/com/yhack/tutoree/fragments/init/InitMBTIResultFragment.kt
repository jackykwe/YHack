package com.yhack.tutoree.fragments.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yhack.tutoree.R
import com.yhack.tutoree.databinding.FragmentInitMbtiResultBinding

class InitMBTIResultFragment : Fragment() {

    private lateinit var binding: FragmentInitMbtiResultBinding
    private val args: InitMBTIResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitMbtiResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Your MBTI\npersonality type is..."
        binding.mbti4LetterTextView.text = "INTP"
        binding.mbtiDescriptionTextView.text =
            "A Logician (INTP) is someone with the Introverted, Intuitive, Thinking, and Prospecting personality traits. These flexible thinkers enjoy taking an unconventional approach to many aspects of life. "
        binding.firstButton.apply {
            text = "OK"
            setOnClickListener {
                findNavController().apply {
                    if (currentDestination?.id == R.id.initMBTIResultFragment) {
                        navigate(
                            InitMBTIResultFragmentDirections.actionInitMBTIResultFragmentToInitSubjectCheckboxFragment(
                                isTutor = args.isTutor,
                                person = args.person
                            )
                        )
                    }
                }
            }
        }
    }
}