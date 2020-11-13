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
import com.yhack.tutoree.activities.Keys
import com.yhack.tutoree.activities.MainActivity
import com.yhack.tutoree.database.Database
import com.yhack.tutoree.database.model.dbexception.EntityNotFoundException
import com.yhack.tutoree.databinding.FragmentLoginBinding
import java.sql.Connection

class LoginFragment : Fragment() {

    companion object {
        fun isTutor(conn: Connection, username: String) = try {
            Database.getTutorByID(conn, username) ?: throw EntityNotFoundException("tutor")
            true
        } catch (e: EntityNotFoundException) {
            try {
                Database.getStudentByID(conn, username) ?: throw EntityNotFoundException("student")
                false
            } catch (e: EntityNotFoundException) {
                throw IllegalStateException("User login succeeded but is neither a tutor or student")
            }
        }
    }

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun proceed(username: String, isTutor: Boolean) {
        findNavController().apply {
            if (currentDestination?.id == R.id.loginFragment) {
                (requireActivity() as MainActivity).sharedPreferences
                    .edit()
                    .putString(Keys.LOGGED_IN, username)
                    .commit()
                navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment(isTutor = isTutor))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Log In"
        binding.firstButton.apply {
            text = "Log in"
            setOnClickListener {
                // Close the keyboard, if it's open
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)

                val username = binding.usernameEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
//                val person = Person(username, password)
                val conn = (requireActivity() as MainActivity).connection!!
                if (Database.login(conn, username, password)) {
                    val isTutor = isTutor(conn, username)
                    proceed(username, isTutor)
                } else {
                    Snackbar.make(
                        binding.root,
                        "Login failed. Please check your credentials.",
                        Snackbar.LENGTH_SHORT
                    ).show()
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