package com.example.onemillonwinner.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment<VDB : ViewDataBinding> : Fragment() {

    abstract val layoutIdFragment: Int
    abstract fun setup()
    private lateinit var _binding: VDB
    protected val binding: VDB
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate<VDB>(inflater, layoutIdFragment, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

}