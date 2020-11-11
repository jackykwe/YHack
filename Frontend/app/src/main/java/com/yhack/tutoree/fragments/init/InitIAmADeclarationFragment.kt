package com.yhack.tutoree.fragments.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.tutoree.R
import com.yhack.tutoree.databinding.FragmentInitIAmADeclarationBinding

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
                findNavController().apply {
                    if (currentDestination?.id == R.id.initIAmADeclarationFragment) {
                        navigate(InitIAmADeclarationFragmentDirections.actionInitIAmADeclarationFragmentToInitTutorInfoFragment())
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
                        navigate(InitIAmADeclarationFragmentDirections.actionInitIAmADeclarationFragmentToInitTuteeInfoFragment())
                    }
                }
            }
        }
    }
}