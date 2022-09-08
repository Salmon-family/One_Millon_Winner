package com.example.onemillonwinner.ui.fragments.game.exitDialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.DialogExitBinding
import com.example.onemillonwinner.ui.base.BaseDialogFragment
import com.example.onemillonwinner.util.EventObserve
import com.example.onemillonwinner.util.extension.setWidthPercent

class ExitDialog : BaseDialogFragment<DialogExitBinding, ExitViewModel>() {

    override val layoutIdFragment = R.layout.dialog_exit
    override val viewModelClass = ExitViewModel::class.java

    override fun setup() {
        setWidthPercent(85)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        exitGame()
    }

    private fun exitGame() {
        viewModel.isExitGame.observe(this,EventObserve{
            if (it) {
                findNavController().popBackStack(R.id.homeFragment, false)
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