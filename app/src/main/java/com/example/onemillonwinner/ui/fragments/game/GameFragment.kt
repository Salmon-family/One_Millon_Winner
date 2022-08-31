package com.example.onemillonwinner.ui.fragments.game

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val gameViewModel: GameViewModel by viewModels()

    override fun setup() {
        binding.gameViewModel = gameViewModel

        observeOnGameDone()
    }

    private fun observeOnGameDone() {
        gameViewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it == GameState.GameOver) {
                    gameViewModel.prize.value?.let { prize ->
                        findNavController().navigate(
                            GameFragmentDirections.actionGameFragmentToResultFragment(prize)
                        )
                    }
                }
            }
        })
    }

    override val layoutIdFragment = R.layout.fragment_game
}