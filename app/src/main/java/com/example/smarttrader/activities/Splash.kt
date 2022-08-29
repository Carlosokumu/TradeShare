package com.example.smarttrader.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.smarttrader.R
import com.example.smarttrader.settings.Settings

class Splash : AppCompatActivity() {
    private var mIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({ //This method will be executed once the timer is over
            // Start your app main activity
            mIntent = if(Settings.hasUserExited()!!){
                Intent(this,AuthActivity::class.java)
            } else {
                Intent(this,MainActivity::class.java)
            }
            startActivity(mIntent)
            // close this activity
            finish()
        }, 5000)

    }
}