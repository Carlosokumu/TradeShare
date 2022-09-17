package com.example.smarttrader

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.thecode.aestheticdialogs.*
import java.util.*


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

fun Context.getDownSideMt4(entryPrice: Double?, stopLoss: Double?): String {
    return if (entryPrice == null || stopLoss == null) {
        "-"
    } else {
        val difference = entryPrice - stopLoss
        val percentageDiff = ((difference / entryPrice) * 100)
        val number3digits: Double = String.format("%.3f", percentageDiff).toDouble()
        number3digits.toString()
    }
}

fun Context. getUpSideMt4(entryPrice: Double?, takeProfit: Double?): String {
    return if (entryPrice == null || takeProfit == null) {
        "-"
    } else {
        val difference = takeProfit - entryPrice
        val percentageDiff = ((difference / entryPrice) * 100)
        val number3digits: Double = String.format("%.3f", percentageDiff).toDouble()
        number3digits.toString()
    }
}
fun Context.calculateDifference(startDate: Date, endDate: Date): String {
    var different = endDate.time.minus(startDate.time)
    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays: Long = different / daysInMilli
    different = different % daysInMilli

    val elapsedHours: Long = different / hoursInMilli
    different = different % hoursInMilli

    val elapsedMinutes: Long = different / minutesInMilli
    different = different % minutesInMilli

    val elapsedSeconds: Long = different / secondsInMilli
    Log.d(
        "DIFFERENCE",
        elapsedDays.toString() + "days" + elapsedHours + "hours" + elapsedMinutes.toString()
    )
    return elapsedDays.toString() + "d" + elapsedHours + "hr" + elapsedMinutes.toString() + "min"

}
