package com.example.onemillonwinner.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.onemillonwinner.BR

abstract class BaseDialogFragment<VDB : ViewDataBinding, VM : BaseViewModel> : DialogFragment() {

    lateinit var viewModel: VM
    abstract val viewModelClass: Class<VM>
    abstract val layoutIdFragment: Int
    private lateinit var _binding: VDB
    protected val binding: VDB
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[viewModelClass]
        _binding = DataBindingUtil.inflate(inflater, layoutIdFragment, container, false)
        _binding.apply {
            lifecycleOwner = this@BaseDialogFragment
            setVariable(BR.viewModel, viewModel)
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        closeDialog()
    }

    abstract fun closeDialog()

    abstract fun setup()
}