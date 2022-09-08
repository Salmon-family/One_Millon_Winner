package com.example.onemillonwinner.ui.fragments.game

import android.media.MediaPlayer
import android.app.AlertDialog
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.HelpFriendDialog
import com.example.onemillonwinner.util.enumState.QuestionState

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
                HelpFriendDialog(requireContext()).show(viewModel.getFriendHelp())
            }
        }
    }

    private fun callBacks() {
        requireActivity().onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showAlertDialog()
                }
            })
    }

    private fun showAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.apply {
            setTitle(R.string.dialog_title)
            setMessage(R.string.dialog_message)
            setIcon(R.drawable.ic_alert_icon)
            setPositiveButton(R.string.yes) { _, _ ->
                findNavController().popBackStack()
            }
            setNegativeButton(R.string.no) { p0, _ ->
                p0.cancel()
            }
        }.create().show()
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

    private fun observeOnAnswersToGiveThemEffect() {
        viewModel.questionState.observe(viewLifecycleOwner) {
            it?.let {
                if (it == QuestionState.QUESTION_SUBMITTED) {
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