package com.example.onemillonwinner.util

import android.content.Context
import android.media.MediaPlayer

class GameMediaPlayer {
    private var mediaPlayer = MediaPlayer()

    fun playSound(context: Context, resourcesId: Int) {
        mediaPlayer = MediaPlayer.create(context, resourcesId)
        mediaPlayer.start()
    }

    fun stopSound() {
        mediaPlayer.stop()
    }

}