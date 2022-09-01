package com.example.onemillonwinner.util

import android.app.AlertDialog
import android.content.Context
import com.example.onemillonwinner.R

class HelpFriendDialog(context: Context) : AlertDialog.Builder(context){
    fun show(correctAnswer: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Call Friend")
        builder.setMessage("I am not sure of the answer but I think it is \n-$correctAnswer")
        builder.setIcon(R.drawable.ic_person_icon)
        builder.setPositiveButton("Ok, Thanks") { _, _ ->
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}