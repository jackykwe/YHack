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
import com.yhack.eetutor.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Sign Up"
        binding.firstButton.apply {
            text = "Sign Up"
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
                    if (currentDestination?.id == R.id.signUpFragment) {
                        navigate(SignUpFragmentDirections.actionSignUpFragmentToInitIAmADeclarationFragment())
                    }
                }
            }
        }

        binding.secondButton.apply {
            text = "Log In"
            setOnClickListener {
                findNavController().apply {
                    if (currentDestination?.id == R.id.signUpFragment) {
                        navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
                    }
                }
            }
        }
    }
}