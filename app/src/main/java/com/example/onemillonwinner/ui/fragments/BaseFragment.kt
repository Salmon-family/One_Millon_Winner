package com.example.onemillonwinner.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    abstract var LOG_TAG: String
    private var _binding: VB? = null
    abstract val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> VB
    protected val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        setup()
        return _binding!!.root
    }

    override fun onStart() {
        super.onStart()
        addCallBacks()
    }

    abstract fun setup()

    abstract fun addCallBacks()

    fun log(value: Any) {
        Log.v(LOG_TAG, value.toString())
    }

}