package com.yhack.eetutor.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.yhack.eetutor.R
import com.yhack.eetutor.activities.Keys
import com.yhack.eetutor.activities.MainActivity
import com.yhack.eetutor.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Log In"
        binding.firstButton.apply {
            text = "Log in"
            setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Email: ${binding.usernameEditText.text} and Password: ${binding.passwordEditText.text}",
                    Toast.LENGTH_SHORT
                ).show()
                (requireActivity() as MainActivity).sharedPreferences
                    .edit()
                    .putInt(Keys.LOGGED_IN, 1)
                    .commit()
                findNavController().apply {
                    if (currentDestination?.id == R.id.loginFragment) {
                        navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    }
                }
            }
        }

        binding.secondButton.apply {
            text = "Sign Up"
            setOnClickListener {
                findNavController().run {
                    if (currentDestination?.id == R.id.loginFragment) {
                        navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).sharedPreferences
            .edit()
            .clear()
            .commit()
    }
}