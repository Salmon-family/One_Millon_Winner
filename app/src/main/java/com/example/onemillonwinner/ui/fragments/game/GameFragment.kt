package com.example.onemillonwinner.ui.fragments.game

import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.HelpFriendDialog

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val gameViewModel: GameViewModel by viewModels()

    override fun setup() {
        binding.gameViewModel = gameViewModel

        observeOnGameDone()
        observeOnCallFriend()
    }

    private fun observeOnCallFriend() {
        gameViewModel.isHelpByFriends.observe(viewLifecycleOwner, Observer { callAFriend ->
            if (callAFriend) {
                HelpFriendDialog(requireContext()).show(gameViewModel.getFriendHelp())
            }
        })
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