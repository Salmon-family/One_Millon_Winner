package com.example.onemillonwinner.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.onemillonwinner.databinding.FragmentHomeBinding
import com.example.onemillonwinner.ui.fragments.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override var LOG_TAG = "HOME_FRAGMENT"

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun addCallBacks() {}

    override fun setup() {
        //only for test
        binding.startGameButton.setOnClickListener {
            startTheGame()
        }
    }

    private fun startTheGame() {
        Navigation.findNavController(binding.root)
            .navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())
    }
}