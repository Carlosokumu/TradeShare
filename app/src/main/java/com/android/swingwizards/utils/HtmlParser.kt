package com.android.swingwizards.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


object HtmlParser {


    suspend fun fetch(url: String) {

        return withContext(Dispatchers.IO) {
            try {
                val document = Jsoup.connect(url).get()
            } catch (e: Exception) {
                throw (e)
            }
        }

    }
}

