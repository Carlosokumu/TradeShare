package com.example.core.utils

import android.content.Context
import com.example.core.R


object Errors {


    fun getErrorString(context: Context, message: String): String {
        val resources = context.resources
        if (message.contains("email")) {
            return resources.getString(R.string.email_exist)
        } else if (message.contains("username")) {
            return resources.getString(R.string.username_exist)
        }
        return  resources.getString(R.string.unknown)


    }
}