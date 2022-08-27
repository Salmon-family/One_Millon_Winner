package com.example.onemillonwinner.ui.fragments.game

import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.ui.fragments.home.HomeFragmentDirections

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val gameViewModel: GameViewModel by viewModels()


    override val layoutIdFragment = R.layout.fragment_game

    override fun setup() {
        binding.gameViewModel = gameViewModel
    }
    fun endTheGame() {
        Navigation.findNavController(binding.root)
            .navigate(GameFragmentDirections.actionGameFragmentToResultFragment())
    }
}