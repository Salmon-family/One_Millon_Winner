package com.example.onemillonwinner.ui.fragments.game

import android.media.MediaPlayer
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.activity.MainActivity
import com.example.onemillonwinner.ui.base.BaseFragment

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val gameViewModel: GameViewModel by viewModels()

    private var mediaPlayer: MediaPlayer? = null

    override fun setup() {
        binding.gameViewModel = gameViewModel

        observeOnGameDone()
    }

    private fun observeOnGameDone() {
        gameViewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == GameState.GameOver) {
                    findNavController().navigate(
                        GameFragmentDirections.actionGameFragmentToResultFragment()
                    )
                    music()
                    stopMusic()
                }
            }
        })
    }

    private fun music() {
        val mediaPlayer = MediaPlayer.create(context, R.raw.woodland_game_winner)
         mediaPlayer.start()
    }

    private fun stopMusic(){
        mediaPlayer?.stop()
    }


    override val layoutIdFragment = R.layout.fragment_game
}