package com.solodilov.rxtest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.solodilov.rxtest.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.requestButton.setOnClickListener {
            findNavController().navigate(R.id.action_main_fragment_to_request_fragment)
        }
        binding.timerButton.setOnClickListener {
            findNavController().navigate(R.id.action_main_fragment_to_timerFragment)
        }
        binding.subjectButton.setOnClickListener {
            findNavController().navigate(R.id.action_main_fragment_to_subjectFragment)
        }
        binding.debounceButton.setOnClickListener {
            findNavController().navigate(R.id.action_main_fragment_to_debounceFragment)
        }
        binding.cardsButton.setOnClickListener {
            findNavController().navigate(R.id.action_main_fragment_to_cardsFragment)
        }
    }
}