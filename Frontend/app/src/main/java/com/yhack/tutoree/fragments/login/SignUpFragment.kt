package com.yhack.tutoree.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.tutoree.R
import com.yhack.tutoree.database.model.Person
import com.yhack.tutoree.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    lateinit var person: Person

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
                val username = binding.usernameEditText.text.toString()
                val password = binding.usernameEditText.text.toString()
                findNavController().apply {
                    if (currentDestination?.id == R.id.signUpFragment) {
                        navigate(
                            SignUpFragmentDirections.actionSignUpFragmentToInitIAmADeclarationFragment(
                                // This will only go into the DB right before the user leaves InitSubjectCheckboxFragment
                                // so we require the user to fully go through the sign up process first
                                // (at least for now; since this is easier to implement - we don't need to track sign up progress)
                                person = Person(username, password)
                            )
                        )
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