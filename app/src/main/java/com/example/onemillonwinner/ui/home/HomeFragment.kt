package com.example.onemillonwinner.ui.home

import androidx.navigation.Navigation
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentHomeBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.EventObserve

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val layoutIdFragment = R.layout.fragment_home
    override val viewModelClass = HomeViewModel::class.java

    override fun setup() {
        startTheGame()
    }

    private fun startTheGame() {
        viewModel.navigateToGameFragment.observe(this, EventObserve {
            if (it) {
                Navigation.findNavController(binding.root)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())
            }
        })
    }
}