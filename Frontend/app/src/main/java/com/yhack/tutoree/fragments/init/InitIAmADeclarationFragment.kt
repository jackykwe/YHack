package com.yhack.tutoree.fragments.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yhack.tutoree.R
import com.yhack.tutoree.database.model.Student
import com.yhack.tutoree.database.model.Tutor
import com.yhack.tutoree.databinding.FragmentInitIAmADeclarationBinding

class InitIAmADeclarationFragment : Fragment() {

    private lateinit var binding: FragmentInitIAmADeclarationBinding
    private val args: InitIAmADeclarationFragmentArgs by navArgs()

    private fun generateTutor(): Tutor = Tutor(args.person.username, args.person.password)
    private fun generateTutee(): Student = Student(args.person.username, args.person.password)

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
                findNavController().apply {
                    if (currentDestination?.id == R.id.initIAmADeclarationFragment) {
                        navigate(
                            InitIAmADeclarationFragmentDirections.actionInitIAmADeclarationFragmentToInitTutorInfoFragment(
                                tutor = generateTutor()
                            )
                        )
                    }
                }
            }
        }

        binding.secondButton.apply {
            text = "Tutee"
            setOnClickListener {
                findNavController().apply {
                    if (currentDestination?.id == R.id.initIAmADeclarationFragment) {
                        navigate(
                            InitIAmADeclarationFragmentDirections.actionInitIAmADeclarationFragmentToInitTuteeInfoFragment(
                                tutee = generateTutee()
                            )
                        )
                    }
                }
            }
        }
    }
}