package com.yhack.eetutor.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yhack.eetutor.R
import com.yhack.eetutor.activities.Keys
import com.yhack.eetutor.activities.MainActivity
import com.yhack.eetutor.databinding.FragmentStartUpBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartUpFragment : Fragment() {

    private lateinit var binding: FragmentStartUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Getting things ready"
        lifecycleScope.launch {
            delay(1000L)  // simulate preparations

            val signedIn: Boolean
            val initSurveyDone: Boolean
            val tutorInitVerifyDone: Boolean
            (requireActivity() as MainActivity).sharedPreferences.run {
                signedIn = getInt(Keys.LOGGED_IN, Int.MIN_VALUE) != Int.MIN_VALUE
                initSurveyDone = getBoolean(Keys.INIT_SURVEY_DONE, false)
                tutorInitVerifyDone = getBoolean(Keys.TUTOR_INIT_VERIFY_DONE, false)
            }
            findNavController().run {
                if (currentDestination?.id == R.id.startUpFragment) {
                    if (signedIn) {
                        if (initSurveyDone) {
                            val isTutor = false // TODO : TEST
                            if (isTutor && !tutorInitVerifyDone) {
                                navigate(StartUpFragmentDirections.actionStartUpFragmentToTutorVerifyFragment())
                            } else {
                                navigate(StartUpFragmentDirections.actionStartUpFragmentToHomeFragment())
                            }
                        } else {
                            navigate(StartUpFragmentDirections.actionStartUpFragmentToInitSurveyFragment())
                        }
                    } else {
                        navigate(StartUpFragmentDirections.actionStartUpFragmentToLoginFragment())
                    }
                }
            }
        }
    }
}
