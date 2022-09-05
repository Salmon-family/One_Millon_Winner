package com.example.onemillonwinner.util

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.LifecycleOwner
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.ui.fragments.game.GameViewModel

class MediaPlayer {
    
    fun observeOnAnswersToGiveThemMusic(context: Context?, viewLifecycleOwner: LifecycleOwner) {
        val viewModel = GameViewModel()
        viewModel.state.observe(viewLifecycleOwner) {
            it?.let {
                if (it == GameState.QUESTION_SUBMITTED) {
                    playMusic(context, R.raw.game_winner)
                }
            }
        }
    }

    fun effectWhenDisplayTheResult(context: Context?, prize: Int) {
        if (prize == 0) {
            playMusic(context, R.raw.loss)
        } else {
            playMusic(context, R.raw.result_game_winner)
        }
    }

    private fun playMusic(context: Context?, resourcesId: Int) {
        val mediaPlayer = MediaPlayer.create(context, resourcesId)
        mediaPlayer.start()
    }
}