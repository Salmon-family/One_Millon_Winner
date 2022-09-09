package com.example.onemillonwinner.ui.result

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentResultBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.GameMediaPlayer

class ResultFragment : BaseFragment<FragmentResultBinding, ResultViewModel>() {

    override val layoutIdFragment = R.layout.fragment_result
    override val viewModelClass = ResultViewModel::class.java
    private val arguments: ResultFragmentArgs by navArgs()
    private val gameMediaPlayer = GameMediaPlayer()

    override fun setup() {
        viewModel.setPrize(arguments.prize)

        navigateToGameFragment()
        navigateToHomeFragment()
        effectWhenDisplayTheResult()
    }

    private fun navigateToGameFragment() {
        viewModel.navigateGame.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.gameFragment)
        }
    }


    private fun navigateToHomeFragment() {
        viewModel.navigateHome.observe(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }


    private fun effectWhenDisplayTheResult() {
        if (viewModel.isHasPrize) {
            gameMediaPlayer.playSound(requireContext(), R.raw.result_game_winner)
        } else {
            gameMediaPlayer.playSound(requireContext(), R.raw.loss)
        }
    }

    override fun onPause() {
        super.onPause()
        gameMediaPlayer.stopSound()
    }

}