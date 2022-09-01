package com.example.onemillonwinner.util

import android.app.AlertDialog
import android.content.Context
import com.example.onemillonwinner.R

class HelpFriendDialog(context: Context) : AlertDialog.Builder(context) {

    fun show(correctAnswer: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.call_friend_title))
        builder.setMessage(context.getString(R.string.friend_help, correctAnswer))
        builder.setIcon(R.drawable.ic_person_icon)
        builder.setPositiveButton(context.getString(R.string.thank_you)) { _, _ ->
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}