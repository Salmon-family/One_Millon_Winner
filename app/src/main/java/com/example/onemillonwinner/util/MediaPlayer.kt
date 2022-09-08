package com.example.onemillonwinner.util

import android.content.Context
import android.media.MediaPlayer
import com.example.onemillonwinner.R

class MediaPlayer {

    fun effectWinner(context: Context?) = playMusic(context, R.raw.game_winner)

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