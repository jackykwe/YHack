package com.yhack.tutoree.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yhack.tutoree.R
import com.yhack.tutoree.activities.Keys
import com.yhack.tutoree.activities.MainActivity
import com.yhack.tutoree.databinding.FragmentStartUpBinding
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

            val username: String?
            val signedIn: Boolean
            (requireActivity() as MainActivity).sharedPreferences.run {
                username = getString(Keys.LOGGED_IN, null)
                signedIn = username != null
            }
            findNavController().run {
                if (currentDestination?.id == R.id.startUpFragment) {
                    if (signedIn) {
                        navigate(
                            StartUpFragmentDirections.actionStartUpFragmentToHomeFragment(
                                isTutor = LoginFragment.isTutor(
                                    (requireActivity() as MainActivity).connection!!,
                                    username!!
                                )
                            )
                        )
                    } else {
                        navigate(StartUpFragmentDirections.actionStartUpFragmentToLoginFragment())
                    }
                }
            }
        }
    }
}
