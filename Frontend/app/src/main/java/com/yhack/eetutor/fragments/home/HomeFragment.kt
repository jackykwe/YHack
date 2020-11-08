package com.yhack.eetutor.fragments.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.eetutor.R
import com.yhack.eetutor.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text =
            "Landing Page"
        binding.firstButton.apply {
            text = "Open someone's full profile"
            setOnClickListener {
                findNavController().run {
                    if (currentDestination?.id == R.id.HomeFragment) {
                        navigate(HomeFragmentDirections.actionHomeFragmentToFullProfileFragment())
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(HomeFragmentDirections.actionGlobalSettingsFragment())
                true
            }
            R.id.action_friends -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}