package com.yhack.tutoree.fragments.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.yhack.tutoree.R
import com.yhack.tutoree.activities.MainActivity
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
                // Close the keyboard, if it's open
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)

                val username = binding.usernameEditText.text.toString()
                val password = binding.usernameEditText.text.toString()
                if (LoginFragment.userExists(
                        (requireActivity() as MainActivity).connection!!,
                        username
                    )
                ) {
                    Snackbar.make(
                        binding.root,
                        "Sorry, that username already exists",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
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