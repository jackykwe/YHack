package com.yhack.tutoree.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.tutoree.R
import com.yhack.tutoree.database.model.Person
import com.yhack.tutoree.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    lateinit var person : Person

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun updatePerson() {
        person = Person(binding.usernameEditText.text.toString(), binding.passwordEditText.text.toString())
//        println(binding.usernameEditText.text)
//        println(binding.passwordEditText.text)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Sign Up"
        binding.firstButton.apply {
            text = "Sign Up"
            setOnClickListener {
                updatePerson()
                Toast.makeText(
                    requireContext(),
                    "Email: ${binding.usernameEditText.text} and Password: ${binding.passwordEditText.text}",
                    Toast.LENGTH_SHORT
                ).show()
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