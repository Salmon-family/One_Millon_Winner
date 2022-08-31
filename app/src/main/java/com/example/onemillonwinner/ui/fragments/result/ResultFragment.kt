package com.example.onemillonwinner.ui.fragments.result


import android.media.MediaPlayer
import androidx.navigation.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentResultBinding
import com.example.onemillonwinner.ui.base.BaseFragment

class ResultFragment : BaseFragment<FragmentResultBinding>() {

    override val layoutIdFragment = R.layout.fragment_result

    private var mediaPlayer: MediaPlayer? = null

    override fun setup() {
        navigateToGameFragment()
        navigateToHomeFragment()
        music()
    }

    private fun navigateToGameFragment() {
        binding.buttonPlayAgain.setOnClickListener {
            it.findNavController().navigate(R.id.gameFragment)
        }
    }

    private fun navigateToHomeFragment() {
        binding.textBackHome.setOnClickListener {
            it.findNavController().popBackStack(R.id.homeFragment, false)
        }
    }
    private fun music() {
        val mediaPlayer = MediaPlayer.create(context, R.raw.woodland_game_winner)
        mediaPlayer.start()
        stopMusic()
    }

    private fun stopMusic(){
        mediaPlayer?.stop()
    }
}