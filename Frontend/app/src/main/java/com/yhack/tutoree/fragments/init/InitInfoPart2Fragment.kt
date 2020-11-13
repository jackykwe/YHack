package com.yhack.tutoree.fragments.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yhack.tutoree.R
import com.yhack.tutoree.database.model.Person
import com.yhack.tutoree.database.model.Student
import com.yhack.tutoree.database.model.Tutor
import com.yhack.tutoree.databinding.FragmentInitInfoPart2Binding

class InitInfoPart2Fragment : Fragment() {

    private lateinit var binding: FragmentInitInfoPart2Binding
    private val args: InitInfoPart2FragmentArgs by navArgs()

    lateinit var person: Person
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitInfoPart2Binding.inflate(inflater, container, false)
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
//                val firstName = binding.firstNameEditText.text.trim().toString()
//                val lastName = binding.lastNameEditText.text.trim().toString()
//                val address = binding.addressEditText.text.trim()
//                    .toString()  // TODO: Not yet saved in database
//                val dateOfBirth = binding.dobEditText.text.trim().toString()
//                val gender = binding.genderEditText.text.trim().toString()
//                val school = binding.schoolEditText.text.trim().toString()
//                val mrExamGrades = binding.mostRecentExamGradesEditText.text.trim().toString()
//                val consent = binding.consentCheckBox.isChecked
                findNavController().apply {
                    if (currentDestination?.id == R.id.initInfoPart2Fragment) {
                        if (args.isTutor) {
                            navigate(
                                InitInfoPart2FragmentDirections.actionInitInfoPart2FragmentToInitTutorVerifyFragment(
                                    tutor = args.person as Tutor
                                )
                            )
                        } else {
                            navigate(
                                InitInfoPart2FragmentDirections.actionInitInfoPart2FragmentToInitMBTIQuestionsFragment(
                                    isTutor = false,
                                    person = args.person as Student
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}