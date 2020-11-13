package com.yhack.tutoree.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yhack.tutoree.R
import com.yhack.tutoree.activities.Keys
import com.yhack.tutoree.activities.MainActivity
import com.yhack.tutoree.database.Database
import com.yhack.tutoree.database.model.Tutor
import com.yhack.tutoree.databinding.FragmentFullProfileBinding

class FullProfileFragment : Fragment() {

    private lateinit var binding: FragmentFullProfileBinding
    private val args: FullProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullProfileBinding.inflate(inflater, container, false)
        binding.person = args.personToDisplay
        binding.executePendingBindings()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).mainActivityBinding.toolbar.title =
            args.personToDisplay.name
    }

    private fun setupChatButton() {
        binding.firstButton.apply {
            text = "Chat"
            visibility = View.VISIBLE
            setOnClickListener {
                findNavController().run {
                    if (currentDestination?.id == R.id.fullProfileFragment) {
                        navigate(
                            FullProfileFragmentDirections.actionFullProfileFragmentToChatFragment(
                                isTutor = args.loggedInUserIsTutor
                            )
                        )
                    }
                }
            }
        }
    }

    private fun setupFavouritesButton(newIsFavourite: Boolean, tutor: Tutor) {
        binding.secondButton.apply {
            text = if (newIsFavourite) "Added to favourites" else "Add to favourites"
            visibility = View.VISIBLE
            setOnClickListener {
                val conn = (requireActivity() as MainActivity).connection
                val currentUserId = (requireActivity() as MainActivity).sharedPreferences.getString(
                    Keys.LOGGED_IN,
                    null
                ) ?: IllegalStateException("User is logged in but sharedPref ID is null")
                Database.updateTeachingStatus(
                    conn,
                    tutor,
                    Database.getStudentByID(conn, currentUserId as String),
                    newIsFavourite,
                    1_000_000
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "About ${args.personToDisplay.name}"

        if (args.loggedInUserIsTutor) {
            // I am a Tutor

//            if ((args.personToDisplay as Student).isOptin || false) {  // TODO: Or conversation already exists
//                setupChatButton()
//            }
        } else {
            // I am a Tutee

            binding.tutorStatsPlaceholderImageView.visibility = View.VISIBLE
            setupChatButton()
            setupFavouritesButton(true, tutor = args.personToDisplay as Tutor) // TODO: Test
        }

    }
}