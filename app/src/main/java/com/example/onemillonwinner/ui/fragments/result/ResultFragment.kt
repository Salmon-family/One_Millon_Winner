package com.example.onemillonwinner.ui.fragments.result

import android.media.MediaPlayer
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentResultBinding
import com.example.onemillonwinner.network.Repository
import com.example.onemillonwinner.ui.base.BaseFragment

class ResultFragment : BaseFragment<FragmentResultBinding>() {

    private val resultViewModel: ResultViewModel by viewModels()
    private val arguments: ResultFragmentArgs by navArgs()

    override val layoutIdFragment = R.layout.fragment_result

    override fun setup() {
        binding.viewModel = resultViewModel

        resultViewModel.setPrize(arguments.prize)

        navigateToGameFragment()
        navigateToHomeFragment()
        effectWhenDisplayTheResult()
    }

    private fun navigateToGameFragment() {
        resultViewModel.navigateGame.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.gameFragment)
        }
    }


    private fun navigateToHomeFragment() {
        resultViewModel.navigateHome.observe(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }


    private fun effectWhenDisplayTheResult() {
        if (arguments.prize == 0) {
            playMusic(R.raw.loss)
        } else {
            playMusic(R.raw.result_game_winner)
        }
    }

    private fun playMusic(resourcesId: Int) {
        val mediaPlayer = MediaPlayer.create(context, resourcesId)
        mediaPlayer.start()
    }
}