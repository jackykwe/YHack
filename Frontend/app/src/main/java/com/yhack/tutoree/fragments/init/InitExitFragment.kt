package com.yhack.tutoree.fragments.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yhack.tutoree.R
import com.yhack.tutoree.activities.Keys
import com.yhack.tutoree.activities.MainActivity
import com.yhack.tutoree.databinding.FragmentInitExitBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InitExitFragment : Fragment() {

    private lateinit var binding: FragmentInitExitBinding
    private val args: InitExitFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitExitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val otherParty = if (args.isTutor) "tutees" else "tutors"
        binding.mainTextView.text = "Your best $otherParty are on their way..." // TODO
        lifecycleScope.launch {
            delay(1000L)
            findNavController().run {
                if (currentDestination?.id == R.id.initExitFragment) {
                    (requireActivity() as MainActivity).sharedPreferences
                        .edit()
                        .putInt(Keys.LOGGED_IN, 1)
                        .commit()
                    navigate(InitExitFragmentDirections.actionInitExitFragmentToHomeFragment(isTutor = args.isTutor))
                }
            }
        }
    }
}