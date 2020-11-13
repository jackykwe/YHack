package com.yhack.tutoree.fragments.init.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yhack.tutoree.R
import com.yhack.tutoree.databinding.FragmentInitTutorInfoBinding
import java.util.*

class InitTutorInfoFragment : Fragment() {

    private lateinit var binding: FragmentInitTutorInfoBinding
    private val args: InitTutorInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitTutorInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Personal Information"
        binding.firstButton.apply {
            text = "Upload your documents"
            setOnClickListener {
                val firstName = binding.firstNameEditText.text.trim().toString()
                val lastName = binding.lastNameEditText.text.trim().toString()
                val address = binding.addressEditText.text.trim()
                    .toString()  // TODO: Not yet saved in database
                val dateOfBirth = binding.dobEditText.text.trim().toString()
                val gender = binding.genderEditText.text.trim().toString()
                val school = binding.schoolEditText.text.trim().toString()
                val mrExamGrades = binding.mostRecentExamGradesEditText.text.trim().toString()
                val pricePerHour = binding.pricePerHourEditText.text.trim().toString()
                findNavController().apply {
                    if (currentDestination?.id == R.id.initTutorInfoFragment) {
                        navigate(
                            InitTutorInfoFragmentDirections.actionInitTutorInfoFragmentToInitInfoPart2Fragment(
                                person = args.tutor.apply {
                                    this.name = "$firstName $lastName"
                                    this.dob =
                                        Date(System.currentTimeMillis())  // TODO: current dateOfBirth field isn't checked for valid date
                                    this.gender = gender
                                    this.school = school
                                    this.academics = mrExamGrades
                                    this.isFulltime = try {
                                        Integer.valueOf(pricePerHour) > 0
                                    } catch (e: Exception) {
                                        false
                                    }  // TODO: replace with a more legit test
                                },
                                isTutor = true
                            )
                        )
                    }
                }
            }
        }
    }
}