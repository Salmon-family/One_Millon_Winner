package com.example.onemillonwinner.ui.fragments.game.callFriendDialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.navigation.fragment.navArgs
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.DialogCallFriendBinding
import com.example.onemillonwinner.ui.base.BaseDialogFragment
import com.example.onemillonwinner.util.EventObserve

class CallFriendDialog : BaseDialogFragment<DialogCallFriendBinding, CallFriendViewModel>(){

    private val arguments: CallFriendDialogArgs by navArgs()
    override val viewModelClass = CallFriendViewModel::class.java
    override val layoutIdFragment = R.layout.dialog_call_friend

    override fun setup() {
        dialogDimensions()
        arguments.correctAnswer?.let { viewModel.setCorrectAnswer(it) }
     }

    private fun dialogDimensions() {
        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        dialog?.window?.setLayout((6 * width)/7, (3 * height)/10)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
     }

    override fun closeDialog() {
        viewModel.isDialogClose.observe(this,EventObserve{
            if(it){
                dismiss()
             }
        })
    }


}