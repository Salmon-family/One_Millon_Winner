package com.example.onemillonwinner.ui.fragments.result


import androidx.navigation.fragment.navArgs
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentResultBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.Preference
import com.example.onemillonwinner.util.Preference.score

class ResultFragment : BaseFragment<FragmentResultBinding>() {
    private val args: ResultFragmentArgs by navArgs()

    override fun setup() {
        setViews()
        saveHighestScoreInSharedPref()
    }

    override val layoutIdFragment = R.layout.fragment_result

    private fun saveHighestScoreInSharedPref() {
        if (receiveScoreFromGameScreen() > score!!) {
            score = receiveScoreFromGameScreen()
        }
    }

    private fun setViews() {
        binding.textLatestScore.text =
            resources.getString(R.string.latest_score, receiveScoreFromGameScreen().toString())
    }

    private fun receiveScoreFromGameScreen(): Int {
        return args.score
    }
}