package com.example.onemillonwinner.ui.fragments.game.exitDialog

import androidx.navigation.fragment.findNavController
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.DialogExitBinding
import com.example.onemillonwinner.ui.base.BaseDialogFragment

class ExitDialog : BaseDialogFragment<DialogExitBinding, ExitViewModel>() {

    override val layoutIdFragment = R.layout.dialog_exit
    override val viewModelClass = ExitViewModel::class.java
    override fun setup() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        exitGame()
    }

    private fun exitGame() {
        viewModel.isExitGame.observe(this) {
            if (it) {
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }

    override fun closeDialog() {
        viewModel.isDialogClose.observe(this) {
            if (it) {
                dismiss()
            }
        }
    }
}