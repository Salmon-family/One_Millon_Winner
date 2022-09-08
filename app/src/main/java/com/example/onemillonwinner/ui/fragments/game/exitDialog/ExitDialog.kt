package com.example.onemillonwinner.ui.fragments.game.exitDialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.DialogExitBinding
import com.example.onemillonwinner.ui.base.BaseDialogFragment
import com.example.onemillonwinner.util.EventObserve

class ExitDialog : BaseDialogFragment<DialogExitBinding, ExitViewModel>() {

    override val layoutIdFragment = R.layout.dialog_exit
    override val viewModelClass = ExitViewModel::class.java

    override fun setup() {
        dialogDimensions()
        exitGame()
    }
    private fun dialogDimensions() {
        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        dialog?.window?.setLayout((6 * width)/7, (3 * height)/10)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun exitGame() {
        viewModel.isExitGame.observe(this,EventObserve{
            if (it) {
                findNavController().navigate(R.id.homeFragment)
            }
        })
    }

    override fun closeDialog() {
        viewModel.isDialogClose.observe(this,EventObserve{
            if (it) {
                dismiss()
            }
        })
    }
}