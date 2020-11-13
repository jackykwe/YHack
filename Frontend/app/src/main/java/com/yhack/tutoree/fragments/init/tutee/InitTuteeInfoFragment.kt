package com.yhack.tutoree.fragments.init.tutee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yhack.tutoree.R
import com.yhack.tutoree.database.model.Person
import com.yhack.tutoree.databinding.FragmentInitTuteeInfoBinding
import java.util.*

class InitTuteeInfoFragment : Fragment() {
    private lateinit var binding: FragmentInitTuteeInfoBinding
    private val args: InitTuteeInfoFragmentArgs by navArgs()
    lateinit var person : Person
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitTuteeInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
//    fun updatePerson() {
//        val firstName = binding.firstNameEditText.text.trim().toString()
//        val lastName = binding.lastNameEditText.text.trim().toString()
//        val address = binding.addressEditText.text.trim()
//            .toString()  // TODO: Not yet saved in database
//        val dateOfBirth = binding.dobEditText.text.trim().toString()
//        val gender = binding.genderEditText.text.trim().toString()
//        val school = binding.schoolEditText.text.trim().toString()
//        val mrExamGrades = binding.mostRecentExamGradesEditText.text.trim().toString()
//        val consent = binding.consentCheckBox.isChecked
//        person = Person(
//            name = "$firstName $lastName",
//            dob = Date(System.currentTimeMillis()),  // TODO: current dateOfBirth field isn't checked for valid date
//            gender = gender,
//            school = school,
//            academics = mrExamGrades,
//            isOptin = consent,
//
//        )
//
//    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Personal Information"
        binding.firstButton.apply {
            text = "Submit"
            setOnClickListener {
                val firstName = binding.firstNameEditText.text.trim().toString()
                val lastName = binding.lastNameEditText.text.trim().toString()
                val address = binding.addressEditText.text.trim()
                    .toString()  // TODO: Not yet saved in database
                val dateOfBirth = binding.dobEditText.text.trim().toString()
                val gender = binding.genderEditText.text.trim().toString()
                val school = binding.schoolEditText.text.trim().toString()
                val mrExamGrades = binding.mostRecentExamGradesEditText.text.trim().toString()
                val consent = binding.consentCheckBox.isChecked
                findNavController().apply {
                    if (currentDestination?.id == R.id.initTuteeInfoFragment) {
                        navigate(
                            InitTuteeInfoFragmentDirections.actionInitTuteeInfoFragmentToInitMBTIQuestionsFragment(
                                isTutor = false,
                                person = args.tutee.apply {
                                    this.name = "$firstName $lastName"
                                    this.dob =
                                        Date(System.currentTimeMillis())  // TODO: current dateOfBirth field isn't checked for valid date
                                    this.gender = gender
                                    this.school = school
                                    this.academics = mrExamGrades
                                    this.isOptin = consent
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}