package com.example.convertapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.convertapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseActivity : AppCompatActivity() {
    abstract val  LOG_TAG : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUp()
        addCallbacks()
    }

    abstract fun setUp()
    abstract fun addCallbacks()

    protected fun log(value:Any){
        Log.v(LOG_TAG,value.toString())
    }

    protected fun showDialog(message:String , title : String){
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(getString(R.string.ok)) { dialog, which ->
            }.show()
    }


}