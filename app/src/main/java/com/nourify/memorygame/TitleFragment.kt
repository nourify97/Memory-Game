package com.nourify.memorygame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.nourify.memorygame.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    private lateinit var binding: FragmentTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTitleBinding.inflate(inflater, container, false)

        // navigating to the game fragment
        binding.startButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_gameFragment)
        }

        return binding.root
    }
}