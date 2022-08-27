package com.example.onemillonwinner.ui.fragments.game

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val gameViewModel: GameViewModel by viewModels()


    override fun setup() {
        binding.gameViewModel = gameViewModel
    }

    override val layoutIdFragment = R.layout.fragment_game

    private fun navigateToResultScreen(view: View, score: Int) {
        val action = GameFragmentDirections.actionGameFragmentToResultFragment(score)
        Navigation.findNavController(view).navigate(action)
    }
}