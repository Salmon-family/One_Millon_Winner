package com.example.onemillonwinner.ui.fragments.result


import android.media.MediaPlayer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentResultBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.Constants.KEY_SCORE
import com.example.onemillonwinner.util.Preference

class ResultFragment : BaseFragment<FragmentResultBinding>(viewModel = ResultViewModel()) {

    override val layoutIdFragment = R.layout.fragment_result
    private val arguments: ResultFragmentArgs by navArgs()

    override fun setup() {
        val prize = arguments.prize
        setBestPrize(prize)
        binding.prize = prize

        navigateToGameFragment()
        navigateToHomeFragment()
        effectWhenDisplayTheResult()
    }

    private fun setBestPrize(currentPrize: Int) {
        val lastPrize = Preference.getInt(KEY_SCORE)
        if (lastPrize != null) {
            if (lastPrize < currentPrize) {
                Preference.setInt(currentPrize, KEY_SCORE)
            }
        }
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