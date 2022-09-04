package com.example.onemillonwinner.ui.fragments.home

import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.FragmentHomeBinding
import com.example.onemillonwinner.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val layoutIdFragment = R.layout.fragment_home
    override val viewModelClass = HomeViewModel::class.java

    override fun setup() {
        startTheGame()
    }

    private fun startTheGame() {
        homeViewModel.navigateToGameFragment.observe(this,EventObserve{
            if(it){
                Navigation.findNavController(binding.root)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())}
        })
        }
}