package com.example.onemillonwinner.ui.fragments.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.onemillonwinner.databinding.FragmentGameBinding
import com.example.onemillonwinner.ui.fragments.BaseFragment

class GameFragment : BaseFragment<FragmentGameBinding>() {

    override var LOG_TAG = "GAME_FRAGMENT"
    private val gameViewModel: GameViewModel by viewModels()

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentGameBinding
        get() = FragmentGameBinding::inflate

    override fun addCallBacks() {}

    override fun setup() {
        binding.lifecycleOwner = this
        binding.gameViewModel = gameViewModel
    }
}