package com.yhack.eetutor.fragments.init

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
import com.yhack.eetutor.databinding.FragmentInitExitBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InitExitFragment : Fragment() {

    private lateinit var binding: FragmentInitExitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitExitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Your best tutors/tutees are on their way..." // TODO
        lifecycleScope.launch {
            delay(1000L)
            findNavController().run {
                if (currentDestination?.id == R.id.initExitFragment) {
                    (requireActivity() as MainActivity).sharedPreferences
                        .edit()
                        .putInt(Keys.LOGGED_IN, 1)
                        .commit()
                    navigate(InitExitFragmentDirections.actionInitExitFragmentToHomeFragment())
                }
            }
        }
    }
}