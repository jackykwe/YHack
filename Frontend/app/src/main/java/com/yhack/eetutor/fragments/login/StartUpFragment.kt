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

            findNavController().run {
                if (currentDestination?.id == R.id.startUpFragment) {
                    if ((requireActivity() as MainActivity).sharedPreferences.getInt(
                            Keys.LOGGED_IN,
                            Int.MIN_VALUE
                        ) != Int.MIN_VALUE
                    ) {
                        navigate(StartUpFragmentDirections.actionStartUpFragmentToDashboardFragment())
                    } else {
                        navigate(StartUpFragmentDirections.actionStartUpFragmentToLoginFragment())
                    }
                }
            }
        }
    }
}
