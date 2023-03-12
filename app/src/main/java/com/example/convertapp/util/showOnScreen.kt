package com.example.convertapp.util

import com.example.convertapp.MainActivity
import com.example.convertapp.util.Constant.OK
import com.google.android.material.dialog.MaterialAlertDialogBuilder


 fun MainActivity.showDialog(message:String , title : String ){
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setNeutralButton(OK) { dialog, which ->
        }.show()
}