package com.yhack.tutoree.fragments.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yhack.tutoree.databinding.FragmentFilterBinding

class FilterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBinding
    private val args: FilterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        (dialog as BottomSheetDialog).apply {
            behavior.isDraggable = false
            setOnShowListener {
                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            super.setupDialog(dialog, style)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (args.isTutor) {
            binding.experienceLL.visibility = View.GONE
            binding.gradeLL.visibility = View.VISIBLE
        } else {
            binding.experienceLL.visibility = View.VISIBLE
            binding.gradeLL.visibility = View.GONE
        }

        binding.exitButton.setOnClickListener { findNavController().navigateUp() }
        binding.firstButton.apply {
            text = "Reset"
            setOnClickListener {
                listOf(
                    binding.mathCheckbox,
                    binding.englishCheckbox,
                    binding.chineseCheckbox,
                    binding.chemistryCheckbox,
                    binding.physicsCheckbox,
                    binding.biologyCheckbox,
                    binding.geographyCheckbox,
                    binding.historyCheckbox,
                    binding.literatureCheckbox,
                    binding.maleCheckbox,
                    binding.femaleCheckbox,
                    binding.nonBinaryCheckbox,
                    binding.partTimeRadioButton,
                    binding.fullTimeRadioButton
                ).forEach {
                    it.isChecked = false
                }
            }
        }
        binding.secondButton.apply {
            text = "Filter"
            setOnClickListener {
                findNavController().navigateUp()
                // Do something to filter
            }
        }
    }
}