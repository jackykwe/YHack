package com.yhack.eetutor.fragments.init.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.eetutor.R
import com.yhack.eetutor.activities.Keys
import com.yhack.eetutor.activities.MainActivity
import com.yhack.eetutor.databinding.FragmentInitTutorVerifyBinding
import com.yhack.eetutor.fragments.init.InitTutorSurveyFragmentDirections

class InitTutorVerifyFragment : Fragment() {

    private lateinit var binding: FragmentInitTutorVerifyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitTutorVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Signing up to be tutor: verifying credentials"
        binding.firstButton.apply {
            text = "Submit cert, Photo ID"
            setOnClickListener {
                (requireActivity() as MainActivity).sharedPreferences
                    .edit()
                    .putBoolean(Keys.Companion.TUTOR_INIT_VERIFY_DONE, true)
                    .commit()
                findNavController().run {
                    if (currentDestination?.id == R.id.initTutorVerifyFragment) {
                        navigate(InitTutorSurveyFragmentDirections.actionInitTutorSurveyFragmentToHomeFragment())
                    }
                }
            }
        }
    }
}