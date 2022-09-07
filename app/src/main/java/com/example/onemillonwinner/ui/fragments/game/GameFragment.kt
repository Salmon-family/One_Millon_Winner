package com.example.onemillonwinner.ui.fragments.game

import android.media.MediaPlayer
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment

class GameFragment : BaseFragment<FragmentGameBinding, GameViewModel>() {

    override val layoutIdFragment = R.layout.fragment_game
    override val viewModelClass = GameViewModel::class.java

    override fun setup() {
        callBacks()
        observeOnGameDone()
        observeOnCallFriend()
        observeOnAnswersToGiveThemEffect()
    }

    private fun observeOnCallFriend() {
        viewModel.isHelpByFriends.observe(viewLifecycleOwner) { callAFriend ->
            if (callAFriend) {
                findNavController().navigate(GameFragmentDirections
                    .actionGameFragmentToHelpFriendDialog(viewModel.getFriendHelp()))
             }
        }
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
        viewModel.state.observe(viewLifecycleOwner) {
            if (it == GameState.GameOver) {
                viewModel.prize.value?.let { prize ->
                    findNavController().navigate(
                        GameFragmentDirections.actionGameFragmentToResultFragment(prize)
                    )
                }
            }
        }
    }

    private fun observeOnAnswersToGiveThemEffect() {
        viewModel.state.observe(viewLifecycleOwner) {
            it?.let {
                if (it == GameState.QUESTION_SUBMITTED) {
                    playMusic(R.raw.game_winner)
                }
            }
        }
    }

    private fun playMusic(resourcesId: Int) {
        val mediaPlayer = MediaPlayer.create(context, resourcesId)
        mediaPlayer.start()
    }

}