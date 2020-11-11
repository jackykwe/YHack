package com.yhack.tutoree.fragments.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yhack.tutoree.R
import com.yhack.tutoree.databinding.FragmentHomeBinding

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

        binding.homeRV.apply {
            setHasFixedSize(true)
            adapter = HomeRVAdapter(
                itemOnClickListener = HomeOnClickListener { view, pid ->
                    findNavController().run {
                        Toast.makeText(
                            requireContext(),
                            "You chose pid: ${pid}.",
                            Toast.LENGTH_SHORT
                        ).show()
//                        if (currentDestination?.id == R.id.homeFragment) {
//                            navigate(
//                                HomeFragmentDirections.actionHomeFragmentToFullProfileFragment()
//                            )
//                        }
                    }
                }
            )
        }
        (binding.homeRV.adapter as HomeRVAdapter).submitList2(listOf(1, 2, 3, 4, 56))
//        (binding.homeRV.adapter as HomeRVAdapter).submitList2(listOf())
        return binding.root
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