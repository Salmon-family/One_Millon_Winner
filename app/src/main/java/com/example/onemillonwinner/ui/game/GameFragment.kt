package com.example.onemillonwinner.ui.game

import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.EventObserve
import com.example.onemillonwinner.util.GameMediaPlayer
import com.example.onemillonwinner.util.enumState.QuestionState

class GameFragment : BaseFragment<FragmentGameBinding, GameViewModel>() {

    override val layoutIdFragment = R.layout.fragment_game
    override val viewModelClass = GameViewModel::class.java
    private val gameMediaPlayer = GameMediaPlayer()

    override fun setup() {
        observeOnCallFriend()
        observeOnGameDone()
        showExitDialogWhenBackButtonPressed()
        observeOnAnswersToGiveThemEffect()
    }

    private fun observeOnCallFriend() {
        viewModel.isCallingFriendClicked.observe(this, EventObserve{
            if (it) {
                findNavController().navigate(GameFragmentDirections
                    .actionGameFragmentToHelpFriendDialog(viewModel.getFriendHelp()))
             }
        })
    }

    private fun observeOnGameDone() {
        viewModel.questionState.observe(viewLifecycleOwner) {
            if (it == QuestionState.GAME_OVER) {
                viewModel.prize.value?.let { prize ->
                    findNavController().navigate(
                        GameFragmentDirections.actionGameFragmentToResultFragment(prize.value)
                    )
                }
            }
        }
    }

    private fun showExitDialogWhenBackButtonPressed() {
        requireActivity().onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(GameFragmentDirections
                        .actionGameFragmentToExitDialog())
                }
            })
    }

    private fun observeOnAnswersToGiveThemEffect() {
        viewModel.questionState.observe(this) {
            it?.let {
                if (it == QuestionState.QUESTION_SUBMITTED) {
                    gameMediaPlayer.playSound(requireContext(), R.raw.game_winner)
                }
            }
        }
    }

}