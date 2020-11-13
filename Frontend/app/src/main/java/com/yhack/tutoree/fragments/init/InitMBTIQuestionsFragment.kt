package com.yhack.tutoree.fragments.init

import android.annotation.SuppressLint
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
import com.yhack.tutoree.databinding.FragmentInitMbtiQuestionsBinding

@SuppressLint("NewApi")
class InitMBTIQuestionsFragment : Fragment() {

    private lateinit var binding: FragmentInitMbtiQuestionsBinding
    private val args: InitMBTIQuestionsFragmentArgs by navArgs()

//    results are saved as a string of a 4 bit integer
    var MBTI : Int = 0

    private fun responsesToPersonality() {
        for (i in 1..4)
            MBTI = MBTI or ((responses[i]?.minus(1) ?: 0) shl (i - 1))
    }

    private val responses = mutableMapOf<Int, Int?>(
        1 to null,
        2 to null,
        3 to null,
        4 to null
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitMbtiQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun toggleButtons(
        question: Int,
        selected: Int,
        buttonLeft: Button,
        buttonRight: Button
    ) {
        responses[question] = selected
        if (selected == 1) {
            buttonLeft.backgroundTintList =
                resources.getColorStateList(R.color.mbtiSelected, requireActivity().theme)
            buttonRight.backgroundTintList =
                resources.getColorStateList(R.color.mbtiUnselect, requireActivity().theme)
        } else if (selected == 2) {
            buttonLeft.backgroundTintList =
                resources.getColorStateList(R.color.mbtiUnselect, requireActivity().theme)
            buttonRight.backgroundTintList =
                resources.getColorStateList(R.color.mbtiSelected, requireActivity().theme)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val otherParty = if (args.isTutor) "tutee" else "tutor"
        binding.apply {
            mainTextView.text = "Personality Test"
            subTextView.text =
                "Here is a 2 minute personality text to help\nus find the most suitable $otherParty for you!"

            // This isn't the most efficient way to implement it, but it's simple (for interest of time)
            // By right a LinearLayout should be used, then we inject every group of question and options
            // into the LinearLayout as required
            q1TextView.apply {
                visibility = View.VISIBLE
                text =
                    "You are totally exhausted because your week was endless and less than great. How are you going to spend your weekend?"
            }
            q1LinearLayout.visibility = View.VISIBLE
            q1aButton.apply {
                text =
                    "I'll call my friends to ask about their plans. We should all go out together."
                setOnClickListener { toggleButtons(1, 1, this, q1bButton) }
            }
            q1bButton.apply {
                text =
                    "I'll switch on the \"Don't disturb\" mode on my phone and stay at home."
                setOnClickListener { toggleButtons(1, 2, q1aButton, this) }
            }

            q2TextView.apply {
                visibility = View.VISIBLE
                text = " Which of these 2 descriptions suits you more?"
            }
            q2LinearLayout.visibility = View.VISIBLE
            q2aButton.apply {
                text =
                    "The most important thing for me is what's happening here and now. I assess real situations and pay attention to details."
                setOnClickListener { toggleButtons(2, 1, this, q2bButton) }
            }
            q2bButton.apply {
                text =
                    "Facts are boring. I love to dream and play over upcoming events in my mind. I rely more on intuition than information."
                setOnClickListener { toggleButtons(2, 2, q2aButton, this) }
            }

            q3TextView.apply {
                visibility = View.VISIBLE
                text =
                    "A competitor of your current employer is trying to entice you. You have doubts because the salary is much higher there, but the staff here is great. How are you going to make a decision?"
            }
            q3LinearLayout.visibility = View.VISIBLE
            q3aButton.apply {
                text =
                    "The most important thing for me is what's happening here and now. I assess real situations and pay attention to details."
                setOnClickListener { toggleButtons(3, 1, this, q3bButton) }
            }
            q3bButton.apply {
                text =
                    "Facts are boring. I love to dream and play over upcoming events in my mind. I rely more on intuition than information."
                setOnClickListener { toggleButtons(3, 2, q3aButton, this) }
            }

            q4TextView.apply {
                visibility = View.VISIBLE
                text = "Do you spend more time indoors or outdoors?"
            }
            q4LinearLayout.visibility = View.VISIBLE
            q4aButton.apply {
                text = "Indoors"
                setOnClickListener { toggleButtons(4, 1, this, q4bButton) }
            }
            q4bButton.apply {
                text = "Outodors"
                setOnClickListener { toggleButtons(4, 2, q4aButton, this) }
            }

            firstButton.apply {
                text = "Submit"
                setOnClickListener {
                    if (responses.all { entry -> entry.value != null }) {

                        responsesToPersonality()
//                        Snackbar.make(
//                            binding.root,
//                            MBTI.toString(),
//                            Snackbar.LENGTH_SHORT
//                        ).show()
                        findNavController().apply {
                            if (currentDestination?.id == R.id.initMBTIQuestionsFragment) {
                                navigate(
                                    InitMBTIQuestionsFragmentDirections.actionInitMBTIQuestionsFragmentToInitMBTIResultFragment(
                                        isTutor = args.isTutor,
                                        person = args.person.apply { personality = MBTI }
                                    )
                                )
                            }
                        }
                    } else {
                        Snackbar.make(
                            binding.root,
                            "You haven't answered all questions!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}