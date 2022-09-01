package com.example.onemillonwinner.ui.fragments.game

import android.media.MediaPlayer
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val gameViewModel: GameViewModel by viewModels()

    private var mediaPlayer: MediaPlayer? = null

    override val layoutIdFragment = R.layout.fragment_game

    override fun setup() {
        binding.gameViewModel = gameViewModel

        observeOnGameDone()
        observeOnAnswersToGiveThemEffect()
    }

    private fun observeOnGameDone() {
        gameViewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == GameState.GameOver) {
                    findNavController().navigate(
                        GameFragmentDirections.actionGameFragmentToResultFragment()
                    )
                }
            }
        })
    }

    private fun observeOnAnswersToGiveThemEffect() {
        gameViewModel.state.observe(viewLifecycleOwner) {
            it?.let {
                if (it == GameState.QUESTION_SUBMITTED) {
                    playMusic(R.raw.game_winner)
                } else if (it == GameState.WRONG_ANSWER_SUBMITTED || isQuestionTimeOver()) {
                    playMusic(R.raw.loss)
                }
            }
        }
    }

    private fun isQuestionTimeOver(): Boolean = gameViewModel.questionTimeOver.value == true

    private fun playMusic(resourcesId: Int) {
        val mediaPlayer = MediaPlayer.create(context, resourcesId)
        mediaPlayer.start()
        stopMusic()
    }

    private fun stopMusic() {
        mediaPlayer?.stop()
    }

}