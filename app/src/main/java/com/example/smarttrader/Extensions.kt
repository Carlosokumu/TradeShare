package com.example.smarttrader

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.thecode.aestheticdialogs.*


fun Context.showErrorToast(message: String){
    AestheticDialog.Builder(this as Activity, DialogStyle.TOASTER, DialogType.ERROR)
        .setTitle("Error")
        .setMessage(message)
        .setCancelable(false)
        .setDarkMode(true)
        .setGravity(Gravity.CENTER)
        .setAnimation(DialogAnimation.SHRINK)
        .setOnClickListener(object : OnDialogClickListener {
            override fun onClick(dialog: AestheticDialog.Builder) {
                dialog.dismiss()
                //actions...
            }
        })
        .show()
}

fun Context.showSuccessMessage(message: String){
    AestheticDialog.Builder(this as Activity, DialogStyle.TOASTER, DialogType.SUCCESS)
        .setTitle("Success")
        .setMessage(message)
        .setCancelable(false)
        .setDarkMode(true)
        .setGravity(Gravity.CENTER)
        .setAnimation(DialogAnimation.SHRINK)
        .setOnClickListener(object : OnDialogClickListener {
            override fun onClick(dialog: AestheticDialog.Builder) {
                dialog.dismiss()

                //actions...
            }
        })
        .show()
}


fun Context.showToast(message: String) = Toast.makeText(this,message,Toast.LENGTH_SHORT).show()


fun Context.showSnackBar(view: View){
    Snackbar.make(view,"Check your internet Connection",Snackbar.LENGTH_SHORT).show()
}