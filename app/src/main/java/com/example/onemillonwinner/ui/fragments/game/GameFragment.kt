package com.example.onemillonwinner.ui.fragments.game

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.CountDownTimer
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val gameViewModel: GameViewModel by viewModels()


    override val layoutIdFragment = R.layout.fragment_game

    override fun setup() {
        binding.gameViewModel = gameViewModel
        questionTimer(10000)
    }

    private fun questionTimer(timeInMilliseconds: Long) {
        timerProgressBar(timeInMilliseconds)
        timerText(timeInMilliseconds)
    }

    private fun timerProgressBar(timeTimer: Long) {
        val animation = ObjectAnimator.ofInt(binding.progressBar, "progress", 100, 0)
        animation.duration = timeTimer
        animation.interpolator = DecelerateInterpolator()
        animation.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animator: Animator) {}

            override fun onAnimationEnd(animator: Animator) {
                //do something when the countdown is complete
            }

            override fun onAnimationCancel(animator: Animator) {}

            override fun onAnimationRepeat(animator: Animator) {}
        })
        animation.start()
    }

    private fun timerText(timeTimer: Long) {
        object : CountDownTimer(timeTimer, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.texTimer.text = "${millisUntilFinished / 1000}"
                // logic to set the EditText could go here
            }

            override fun onFinish() {
                //do something when the countdown is complete

                endTheGame()
            }
        }.start()
    }


    private fun endTheGame() {
        Navigation.findNavController(binding.root)
            .navigate(GameFragmentDirections.actionGameFragmentToResultFragment())
    }
}