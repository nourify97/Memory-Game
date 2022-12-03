package com.nourify.memorygame

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.nourify.memorygame.databinding.FragmentGameEndBinding


class GameEndFragment : Fragment() {
    private lateinit var binding: FragmentGameEndBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameEndBinding.inflate(inflater, container, false)

        // Retrieving the score from the bundle
        val timeToFinish = arguments?.getInt("timeToFinish")
        // Format seconds to minutes
        val minuteFormat = timeToFinish?.let { secondsToMinuteFormat(it) }

        val timeResult = binding.timeScore
        val outputText = "Congrats you finished \nthe game in : $minuteFormat"
        timeResult.text = outputText

        binding.restartButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_gameEnd_to_gameFragment)
        }

        return binding.root
    }
}

fun secondsToMinuteFormat(seconds: Int): String {
    val s = seconds % 60
    var h = seconds / 60
    val m = h % 60
    return "$m:$s min"
}