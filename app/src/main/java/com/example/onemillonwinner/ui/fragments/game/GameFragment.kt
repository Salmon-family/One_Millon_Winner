package com.example.onemillonwinner.ui.fragments.game

import android.media.MediaPlayer
import android.app.AlertDialog
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.HelpFriendDialog

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val gameViewModel: GameViewModel by viewModels()

    private var mediaPlayer: MediaPlayer? = null

    override val layoutIdFragment = R.layout.fragment_game

    override fun setup() {
        binding.gameViewModel = gameViewModel
        callBacks()
        observeOnGameDone()
        observeOnCallFriend()
        observeOnAnswersToGiveThemEffect()
    }

    private fun observeOnCallFriend() {
        gameViewModel.isHelpByFriends.observe(viewLifecycleOwner) { callAFriend ->
            if (callAFriend) {
                HelpFriendDialog(requireContext()).show(gameViewModel.getFriendHelp())
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
        gameViewModel.state.observe(viewLifecycleOwner) {
            if (it == GameState.GameOver) {
                gameViewModel.prize.value?.let { prize ->
                    findNavController().navigate(
                        GameFragmentDirections.actionGameFragmentToResultFragment(prize)
                    )
                }
            }
        }
    }

    private fun observeOnAnswersToGiveThemEffect() {
        gameViewModel.state.observe(viewLifecycleOwner) {
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
        stopMusic()
    }

    private fun stopMusic() {
        mediaPlayer?.stop()
    }

}