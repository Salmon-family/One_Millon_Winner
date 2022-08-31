package com.example.onemillonwinner.ui.fragments.result


import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentResultBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.ui.fragments.game.GameViewModel

class ResultFragment : BaseFragment<FragmentResultBinding>() {

    override val layoutIdFragment = R.layout.fragment_result
    private val arguments: ResultFragmentArgs by navArgs()

    override fun setup() {
        val prize = arguments.prize
        binding.prize = prize

        navigateToGameFragment()
        navigateToHomeFragment()
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