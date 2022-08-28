package com.example.onemillonwinner.ui.fragments.home

import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentHomeBinding
import com.example.onemillonwinner.ui.base.BaseFragment
import com.example.onemillonwinner.util.EventObserve

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun setup() {
        binding.homeViewModel = homeViewModel
        //only for test
        startTheGame()
    }

    private fun startTheGame() {
        homeViewModel.naveToGameFragment.observe(viewLifecycleOwner, EventObserve{
            if(it){
                Navigation.findNavController(binding.root)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())
            }
        })
    }

    override val layoutIdFragment = R.layout.fragment_home
}