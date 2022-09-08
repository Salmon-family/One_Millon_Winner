package com.example.onemillonwinner.ui.game

import android.media.MediaPlayer
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.EventObserve
import com.example.onemillonwinner.util.enumState.QuestionState

class GameFragment : BaseFragment<FragmentGameBinding, GameViewModel>() {

    override val layoutIdFragment = R.layout.fragment_game
    override val viewModelClass = GameViewModel::class.java
    private var mediaPlayer = MediaPlayer()

    override fun setup() {
        observeOnMusic()
        callBacks()
        observeOnGameDone()
        observeOnCallFriend()
    }

    private fun observeOnMusic() = mediaPlayer.observeOnAnswersToGiveThemMusic(context, viewLifecycleOwner)

    private fun observeOnCallFriend() {
        viewModel.isHelpByFriends.observe(this, EventObserve{
            if (it) {
                findNavController().navigate(GameFragmentDirections
                    .actionGameFragmentToHelpFriendDialog(viewModel.getFriendHelp()))
             }
        })
    }

    private fun callBacks() {
        requireActivity().onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(GameFragmentDirections
                        .actionGameFragmentToExitDialog())
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
}