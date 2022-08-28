package com.example.onemillonwinner.ui.fragments.game

import androidx.databinding.ObservableInt
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.State
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.databinding.FragmentGameBindingImpl
import com.example.onemillonwinner.databinding.FragmentGameTestBinding
import com.example.onemillonwinner.ui.base.BaseFragment

class GameFragment : BaseFragment<FragmentGameTestBinding>() {

    private val gameViewModel: GameViewModel by viewModels()

    override fun setup() {
        binding.gameViewModel = gameViewModel

        observeOnGameDone()
    }

    private fun observeOnGameDone() {
        gameViewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it is State.Complete) {
                    findNavController().navigate(
                        GameFragmentDirections.actionGameFragmentToResultFragment()
                    )
                }
            }
        })
    }

    override val layoutIdFragment = R.layout.fragment_game_test
}