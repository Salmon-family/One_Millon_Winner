package com.example.onemillonwinner.ui.fragments.game

import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.data.GameState
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.BalloonFactory
import com.example.onemillonwinner.util.HelpFriendDialog
import com.example.onemillonwinner.util.PublicVoteChart
import com.example.onemillonwinner.util.extension.buildBalloon
import com.example.onemillonwinner.util.extension.closeBalloon
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.skydoves.balloon.balloon

class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val gameViewModel: GameViewModel by viewModels()
    private val balloonFactory by balloon<BalloonFactory>()

    override fun setup() {
        binding.gameViewModel = gameViewModel

        onClickFromViewModel()
        balloonFactory.closeBalloon()
    }

    private fun onClickFromViewModel() {
        gameViewModel.onEventClick.observe(this) {
            when (it) {
                is GameViewModel.OnEventClick.OnAudienceVoteHelpToolClick -> {
                    balloonFactory.showAtCenter(binding.root)
                    getQuestions()
                }
                is GameViewModel.OnEventClick.WhenTheGameEnd -> {
                    gameViewModel.prize.value?.let { prize ->
                        findNavController().navigate(
                            GameFragmentDirections.actionGameFragmentToResultFragment(prize)
                        )
                    }
                }
                is GameViewModel.OnEventClick.OnClickFriendCall ->{
                    HelpFriendDialog(requireContext()).show(gameViewModel.getFriendHelp())
                }
            }
        }
    }

    private fun getQuestions() {
        gameViewModel.question.observe(this) {
            balloonFactory.buildBalloon(it.getAnswers(), it.getCorrectAnswer())
        }
    }

    override val layoutIdFragment = R.layout.fragment_game
}