package com.example.onemillonwinner.ui.fragments.result


import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentResultBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.Constants.KEY_SCORE
import com.example.onemillonwinner.util.MediaPlayer
import com.example.onemillonwinner.util.Preference

class ResultFragment : BaseFragment<FragmentResultBinding, ResultViewModel>() {

    override val layoutIdFragment = R.layout.fragment_result
    override val viewModelClass = ResultViewModel::class.java
    private val arguments: ResultFragmentArgs by navArgs()
    private val mediaPlayer = MediaPlayer()

    override fun setup() {
        val prize = arguments.prize
        setBestPrize(prize)
        binding.prize = prize

        startMusic()
        navigateToGameFragment()
        navigateToHomeFragment()
    }

    private fun startMusic() = mediaPlayer.effectWhenDisplayTheResult(context, arguments.prize)

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
}