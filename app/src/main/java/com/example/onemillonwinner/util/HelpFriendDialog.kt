package com.example.onemillonwinner.util

import android.app.AlertDialog
import android.content.Context
import com.example.onemillonwinner.R

class HelpFriendDialog(context: Context) : AlertDialog.Builder(context){

    fun show(correctAnswer: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.call_friend_title))
        builder.setMessage(context.getString(R.string.friend_help,correctAnswer ))
        builder.setIcon(R.drawable.ic_person_icon)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)

        alertDialog.show()
    }













//    fun showDialog(correctAnswer:String,listener:(r : Boolean) -> Unit){
//        val builder = AlertDialog.Builder(context)
//        onResponse = listener
//        builder.setTitle("Friend Call")
//        builder.setMessage("I'm not sure of the answer but I think it's \n $correctAnswer")
//
//        builder.setPositiveButton("Ok, Thanks"){_,_ ->
//            onResponse(true)
//        }.create()
//
//        val alertDialog: AlertDialog = builder.create()
//
//        alertDialog.setCancelable(false)
//        alertDialog.show()
//    }
}