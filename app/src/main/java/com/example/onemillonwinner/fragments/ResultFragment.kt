package com.example.onemillonwinner.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.onemillonwinner.databinding.FragmentResultBinding

class ResultFragment : BaseFragment<FragmentResultBinding>() {

    override var LOG_TAG = "RESULT_FRAGMENT"

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentResultBinding
        get() = FragmentResultBinding::inflate

    override fun addCallBacks() {}
}