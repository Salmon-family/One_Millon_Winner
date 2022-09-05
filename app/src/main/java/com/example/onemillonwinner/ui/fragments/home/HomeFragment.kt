package com.example.onemillonwinner.ui.fragments.home

import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentHomeBinding
import com.example.onemillonwinner.network.Repository
import com.example.onemillonwinner.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun setup() {
        binding.homeViewModel = homeViewModel
        binding.startGameButton.setOnClickListener {
            startTheGame()
        }
    }

    private fun startTheGame() {
        Navigation.findNavController(binding.root)
            .navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())
    }

    override val layoutIdFragment = R.layout.fragment_home
}