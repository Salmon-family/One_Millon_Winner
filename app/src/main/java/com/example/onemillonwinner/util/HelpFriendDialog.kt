package com.example.onemillonwinner.util

import android.app.AlertDialog
import android.content.Context
import com.example.onemillonwinner.R

class HelpFriendDialog(context: Context) : AlertDialog.Builder(context){
    lateinit var onResponse: (r : Boolean) -> Unit
    fun show(correctAnswer: String, listener: (Boolean) -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Call Friend")
        builder.setMessage("I am not sure of the answer but I think it is \n-$correctAnswer")
        builder.setIcon(R.drawable.ic_person_icon)
        onResponse = listener
        builder.setPositiveButton("Ok, Thanks") { _, _ ->
            onResponse(true)
        }
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