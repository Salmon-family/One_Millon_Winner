package com.example.onemillonwinner.ui.fragments.home

import androidx.navigation.Navigation
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentHomeBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.Preference

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun setup() {
        //only for test
        binding.startGameButton.setOnClickListener {
            startTheGame()
        }
        loadScore()
    }

    private fun startTheGame() {
        Navigation.findNavController(binding.root)
            .navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())
    }

    override val layoutIdFragment = R.layout.fragment_home

    private fun loadScore() {
        if (Preference.score != -1) {
            binding.textHighestScore.text =
                resources.getString(R.string.highest_score, Preference.score.toString())
        }
    }
}