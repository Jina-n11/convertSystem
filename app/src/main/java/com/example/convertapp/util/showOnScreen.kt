package com.example.convertapp.util

import com.example.convertapp.MainActivity
import com.example.convertapp.util.Constant.OK
import com.google.android.material.dialog.MaterialAlertDialogBuilder


 fun showDialog(message:String , title : String , context : MainActivity){
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setNeutralButton(OK) { dialog, which ->
        }.show()
}