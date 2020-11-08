package com.yhack.eetutor.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.eetutor.R
import com.yhack.eetutor.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Chats"
        binding.firstButton.apply {
            text = "View full profile"
            setOnClickListener {
                findNavController().run {
                    if (currentDestination?.id == R.id.chatFragment) {
                        navigate(ChatFragmentDirections.actionChatFragmentToFullProfileFragment())
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_friends, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(ChatFragmentDirections.actionGlobalSettingsFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}