package com.example.smarttrader.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrader.R
import com.example.smarttrader.settings.Settings

class Splash : AppCompatActivity() {
    private var mIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.jmLogo)
        val nameContainer = findViewById<LinearLayout>(R.id.nameContainer)

        val bottomUpAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_up)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        logo.startAnimation(bottomUpAnimation)
        nameContainer.startAnimation(slideAnimation)

        Handler().postDelayed({ //This method will be executed once the timer is over
            // Start your app main activity
            mIntent = if (Settings.hasUserExited()!!) {
                Intent(this, AuthActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java)
            }
            startActivity(mIntent)
            // close this activity
            finish()
        }, 5000)

    }
}