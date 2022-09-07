package com.example.onemillonwinner.ui.fragments.game.callFriendDialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.navigation.fragment.navArgs
import com.example.onemillonwinner.R
import com.example.onemillonwinner.databinding.DialogCallFriendBinding
import com.example.onemillonwinner.ui.base.BaseDialogFragment

class CallFriendDialog : BaseDialogFragment<DialogCallFriendBinding, CallFriendViewModel>(){

    private val arguments: CallFriendDialogArgs by navArgs()
    override val viewModelClass = CallFriendViewModel::class.java
    override val layoutIdFragment = R.layout.dialog_call_friend

    override fun setup() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        arguments.correctAnswer?.let { viewModel.setCorrectAnswer(it) }
     }
    override fun closeDialog() {
        viewModel.isDialogClose.observe(this){
            if(it){
                dismiss()
            }
        }
    }


}