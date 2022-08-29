package com.example.onemillonwinner.ui.fragments.result


import androidx.navigation.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentResultBinding
import com.example.onemillonwinner.ui.base.BaseFragment

class ResultFragment : BaseFragment<FragmentResultBinding>() {

    override val layoutIdFragment = R.layout.fragment_result

    override fun setup() {
        goToGameFragment()
        goToHomeFragment()
    }

    private fun goToGameFragment() {
        binding.buttonPlayAgain.setOnClickListener {
            it.findNavController().navigate(R.id.gameFragment)
        }
    }

    private fun goToHomeFragment() {
        binding.textBackHome.setOnClickListener {
            it.findNavController().popBackStack(R.id.homeFragment, true)
        }
    }
}