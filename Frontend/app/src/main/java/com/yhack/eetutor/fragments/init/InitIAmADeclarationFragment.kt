package com.yhack.eetutor.fragments.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.eetutor.R
import com.yhack.eetutor.activities.Keys
import com.yhack.eetutor.activities.MainActivity
import com.yhack.eetutor.databinding.FragmentInitIAmADeclarationBinding

class InitIAmADeclarationFragment : Fragment() {

    private lateinit var binding: FragmentInitIAmADeclarationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitIAmADeclarationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "I am a..."
        binding.firstButton.apply {
            text = "Tutor"
            setOnClickListener {
                Toast.makeText(requireContext(), "You selected Tutor", Toast.LENGTH_SHORT).show()
                (requireActivity() as MainActivity).sharedPreferences
                    .edit()
                    .putInt(Keys.LOGGED_IN, 1)
                    .commit()
                findNavController().apply {
                    if (currentDestination?.id == R.id.initIAmADeclarationFragment) {
                        navigate(InitIAmADeclarationFragmentDirections.actionInitIAmADeclarationFragmentToInitTutorSurveyFragment())
                    }
                }
            }
        }

        binding.secondButton.apply {
            text = "Tutee"
            setOnClickListener {
                Toast.makeText(requireContext(), "You selected Tutee", Toast.LENGTH_SHORT).show()
                findNavController().apply {
                    if (currentDestination?.id == R.id.initIAmADeclarationFragment) {
                        navigate(InitIAmADeclarationFragmentDirections.actionInitIAmADeclarationFragmentToInitTutorSurveyFragment())
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