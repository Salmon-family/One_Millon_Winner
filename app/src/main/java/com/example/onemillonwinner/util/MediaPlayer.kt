package com.example.onemillonwinner.util

import android.content.Context
import android.media.MediaPlayer

class MediaPlayer {

    fun playMusic(context: Context?, resourcesId: Int) {
        val mediaPlayer = MediaPlayer.create(context, resourcesId)
        mediaPlayer.start()
    }
}