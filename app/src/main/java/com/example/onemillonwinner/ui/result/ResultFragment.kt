package com.example.onemillonwinner.ui.result

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentResultBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.MediaPlayer

class ResultFragment : BaseFragment<FragmentResultBinding, ResultViewModel>() {

    override val layoutIdFragment = R.layout.fragment_result
    override val viewModelClass = ResultViewModel::class.java
    private val arguments: ResultFragmentArgs by navArgs()
    private val mediaPlayer = MediaPlayer()

    override fun setup() {
        viewModel.setPrize(arguments.prize)

        startMusic()
        navigateToGameFragment()
        navigateToHomeFragment()
    }

    private fun startMusic() = mediaPlayer.effectWhenDisplayTheResult(context, arguments.prize)

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
}