package com.yhack.eetutor.fragments.init.tutor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.eetutor.R
import com.yhack.eetutor.databinding.FragmentInitTutorVerifyBinding

class InitTutorVerifyFragment : Fragment() {

    private lateinit var binding: FragmentInitTutorVerifyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitTutorVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Upload your documents"
        binding.firstButton.apply {
            text = "Upload"
            setOnClickListener {
                findNavController().run {
                    if (currentDestination?.id == R.id.initTutorVerifyFragment) {
                        navigate(InitTutorVerifyFragmentDirections.actionInitTutorVerifyFragmentToInitMBTIQuestionsFragment())
                    }
                }
            }
        }
    }
}