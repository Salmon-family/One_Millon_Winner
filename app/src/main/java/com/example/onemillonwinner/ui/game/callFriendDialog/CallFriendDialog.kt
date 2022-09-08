package com.example.onemillonwinner.ui.game.callFriendDialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.navigation.fragment.navArgs
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.DialogCallFriendBinding
import com.example.onemillonwinner.ui.base.BaseDialogFragment
import com.example.onemillonwinner.util.EventObserve
import com.example.onemillonwinner.util.extension.setWidthPercent

class CallFriendDialog : BaseDialogFragment<DialogCallFriendBinding, CallFriendViewModel>(){

    private val arguments: CallFriendDialogArgs by navArgs()
    override val viewModelClass = CallFriendViewModel::class.java
    override val layoutIdFragment = R.layout.dialog_call_friend

    override fun setup() {
        setWidthPercent(85)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        arguments.correctAnswer?.let { viewModel.setCorrectAnswer(it) }
     }

    override fun closeDialog() {
        viewModel.isDialogClose.observe(this,EventObserve{
            if(it){
                dismiss()
             }
        })
    }


}