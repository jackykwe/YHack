package com.yhack.tutoree.fragments.init.tutee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.tutoree.R
import com.yhack.tutoree.database.model.Student
import com.yhack.tutoree.database.model.Tutor
import com.yhack.tutoree.databinding.FragmentInitTuteeInfoBinding
import com.yhack.tutoree.fragments.login.SignUpFragment

class InitTuteeInfoFragment : Fragment() {
    private lateinit var binding: FragmentInitTuteeInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitTuteeInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Personal Information"
        binding.firstButton.apply {
            text = "Submit"
            var student : Student

            setOnClickListener {
                findNavController().apply {
                    if (currentDestination?.id == R.id.initTuteeInfoFragment) {
                        navigate(
                            InitTuteeInfoFragmentDirections.actionInitTuteeInfoFragmentToInitMBTIQuestionsFragment(
                                isTutor = false
                            )
                        )
                    }
                }
            }
        }
    }
}