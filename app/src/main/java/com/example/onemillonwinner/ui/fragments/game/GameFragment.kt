package com.example.onemillonwinner.ui.fragments.game

import android.app.AlertDialog
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.HelpFriendDialog
import com.example.onemillonwinner.util.MediaPlayer

class GameFragment : BaseFragment<FragmentGameBinding, GameViewModel>() {

    override val layoutIdFragment = R.layout.fragment_game
    override val viewModelClass = GameViewModel::class.java
    private var mediaPlayer = MediaPlayer()

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
                    mediaPlayer.playMusic(context,R.raw.game_winner)
                }
            }
        }
    }
}