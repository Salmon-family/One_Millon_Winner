package com.example.onemillonwinner.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.onemillonwinner.databinding.FragmentGameBinding

class GameFragment : BaseFragment<FragmentGameBinding>() {

    override var LOG_TAG = "GAME_FRAGMENT"

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentGameBinding
        get() = FragmentGameBinding::inflate

    override fun addCallBacks() {}
}