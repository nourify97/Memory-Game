package com.nourify.memorygame

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.nourify.memorygame.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var gameTimer: GameTimer
    data class Card(val drawable: Int)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        gameTimer = GameTimer()

        // add a lifecycle observer
        lifecycle.addObserver(gameTimer)

        // Start the Game
        begin()

        return binding.root
    }

    private fun setListeners(cards: List<Card>) {
        binding.apply {
            val clickableViews: MutableList<ImageView> =
                mutableListOf(card01, card02, card03, card04,
                    card05, card06, card07, card08,
                    card09, card10, card11, card12)

            var firstClick: String? = null
            var secondClick: String? = null
            var positionOne: Int? = null
            var positionTwo: Int? = null


            for (item in clickableViews) {
                item.setOnClickListener {
                    revealTheCard(it as ImageView, cards)
                    var match = false

                    // find resource id position
                    val length = resources.getResourceName(it.id).toString().length
                    val position = resources.getResourceName(it.id).substring(length-2).toInt() - 1

                    // handle a single guess
                    if (firstClick == null) {
                        firstClick = cards[position].drawable.toString()
                        positionOne = position
                        clickableViews[positionOne!!].isClickable = false
                    }
                    else {
                        secondClick = cards[position].drawable.toString()
                        positionTwo = position

                        if (firstClick.equals(secondClick) && positionOne != positionTwo) {
                            match = true
                        }

                        if (!match) {
                            clickableViews[positionOne!!].setImageDrawableWithAnimation(requireContext().getDrawable(R.drawable.card)!!, duration = 1000)
                            clickableViews[positionTwo!!].setImageDrawableWithAnimation(requireContext().getDrawable(R.drawable.card)!!, duration = 1500)

                            // vibrate if wrong guess
                            wrongGuessVibration(requireContext())
                            clickableViews[positionOne!!].isClickable = true
                        }
                        else {
                            clickableViews[positionOne!!].isClickable = false
                            clickableViews[positionTwo!!].isClickable = false
                        }

                        firstClick = null
                        secondClick = null
                    }

                    if (gameEnd(clickableViews)) {
                        // Creating a bundle object to pass it to the EndGameFragment
                        val bundle = bundleOf("timeToFinish" to gameTimer.secondsCount)
                        // Navigating to GameEndFragment
                        Navigation.findNavController(it).navigate(R.id.action_gameFragment_to_gameEnd, bundle)
                    }

                }
            }
        }
    }

    private fun gameEnd(clickableView: MutableList<ImageView>): Boolean {
        var gameEnd = true
        for (item in clickableView) {
            if (item.isClickable) {
                gameEnd = false
                break
            }
        }
        return gameEnd
    }


    private fun wrongGuessVibration(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorManager.defaultVibrator
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK))
        } else {
            val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                v.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
            } else {
                v.vibrate(200L)
            }
        }
    }

    private fun revealTheCard(view: ImageView, cards: List<Card>) {
        binding.apply {
            when (view) {
                card01 -> view.setImageResource(cards[0].drawable)
                card02 -> view.setImageResource(cards[1].drawable)
                card03 -> view.setImageResource(cards[2].drawable)
                card04 -> view.setImageResource(cards[3].drawable)
                card05 -> view.setImageResource(cards[4].drawable)
                card06 -> view.setImageResource(cards[5].drawable)
                card07 -> view.setImageResource(cards[6].drawable)
                card08 -> view.setImageResource(cards[7].drawable)
                card09 -> view.setImageResource(cards[8].drawable)
                card10 -> view.setImageResource(cards[9].drawable)
                card11 -> view.setImageResource(cards[10].drawable)
                card12 -> view.setImageResource(cards[11].drawable)
            }
        }
    }

    private fun begin() {
        val cards: List<Card> = listOf(
            Card(R.drawable.cat),
            Card(R.drawable.cat),
            Card(R.drawable.fox),
            Card(R.drawable.fox),
            Card(R.drawable.ippo),
            Card(R.drawable.ippo),
            Card(R.drawable.lion),
            Card(R.drawable.lion),
            Card(R.drawable.monkey),
            Card(R.drawable.monkey),
            Card(R.drawable.mouse),
            Card(R.drawable.mouse)
        ).shuffled()

        setListeners(cards)
    }

    private fun ImageView.setImageDrawableWithAnimation(
        drawable: Drawable,
        duration: Int = 300) {

        val currentDrawable = getDrawable()
        if (currentDrawable == null) {
            setImageDrawable(drawable)
            return
        }

        val transitionDrawable = TransitionDrawable(
            arrayOf(
                currentDrawable,
                drawable
            )
        )
        setImageDrawable(transitionDrawable)
        transitionDrawable.startTransition(duration)
    }
}