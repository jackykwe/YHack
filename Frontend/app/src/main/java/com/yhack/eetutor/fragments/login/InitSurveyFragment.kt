package com.yhack.eetutor.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.yhack.eetutor.R
import com.yhack.eetutor.activities.Keys
import com.yhack.eetutor.activities.MainActivity
import com.yhack.eetutor.databinding.FragmentInitSurveyBinding

class InitSurveyFragment : Fragment() {

    private lateinit var binding: FragmentInitSurveyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitSurveyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Every learner is a good learner"
        binding.firstButton.apply {
            text = "Submit MBTF Survey (Tutee)"
            setOnClickListener {
                (requireActivity() as MainActivity).sharedPreferences
                    .edit()
                    .putBoolean(Keys.INIT_SURVEY_DONE, true)
                    .commit()
                findNavController().apply {
                    if (currentDestination?.id == R.id.initSurveyFragment) {
                        navigate(InitSurveyFragmentDirections.actionInitSurveyFragmentToHomeFragment())
                    }
                }
            }
        }

        binding.secondButton.apply {
            text = "Submit MBTF Survey (Tutor)"
            setOnClickListener {
                (requireActivity() as MainActivity).sharedPreferences
                    .edit()
                    .putBoolean(Keys.INIT_SURVEY_DONE, true)
                    .commit()
                findNavController().apply {
                    if (currentDestination?.id == R.id.initSurveyFragment) {
                        navigate(InitSurveyFragmentDirections.actionInitSurveyFragmentToTutorVerifyFragment())
                    }
                }
            }
        }
    }
}