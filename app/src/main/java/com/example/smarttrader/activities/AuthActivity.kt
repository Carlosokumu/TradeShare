package com.example.smarttrader.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.smarttrader.databinding.ActivityAuthBinding
import com.example.smarttrader.fragments.ResetPass
import com.example.smarttrader.settings.Settings
import com.example.smarttrader.viewmodels.UserViewModel
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import org.koin.android.viewmodel.ext.android.viewModel

class AuthActivity : AppCompatActivity() {

    private  lateinit var binding : ActivityAuthBinding
    private val userViewModel: UserViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)

        setContentView(binding.root)
        updateApp(this)
        userViewModel.getUser(Settings.getUserName()!!)
        userViewModel.user.observe(this, Observer {
            binding.edUserName.setText(it.username)
        })

        binding.txtForgot.setOnClickListener {
            ResetPass.newInstance().show(supportFragmentManager, "reset-password")
        }
        binding.btnLogin.setOnClickListener {
            if (binding.edPassword.text.isBlank()){
                binding.edPassword.error = "Enter your password here"
                return@setOnClickListener
            }
            if(Settings.getPassword() == binding.edPassword.text.toString()){
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            else {
                binding.edPassword.error = "Incorrect password"
            }

        }
    }
    companion object {

        private const val MY_REQUEST_CODE = 104


        fun updateApp(context: Context) {
            val appUpdateManager = AppUpdateManagerFactory.create(context)
            val appUpdateInfoTask = appUpdateManager.appUpdateInfo
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    // Request the update.
                    appUpdateManager.startUpdateFlowForResult(
                        // Pass the intent that is returned by 'getAppUpdateInfo()'.
                        appUpdateInfo,
                        // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                        AppUpdateType.IMMEDIATE,
                        // The current activity making the update request.
                        context as Activity,
                        // Include a request code to later monitor this update request.
                        MY_REQUEST_CODE
                    )

                }
            }

        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                  updateApp(this)
            }
        }
    }
}