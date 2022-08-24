package com.example.onemillonwinner.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.onemillonwinner.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override var LOG_TAG = "HOME_FRAGMENT"

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun addCallBacks() {
        TODO("Not yet implemented")
    }
}