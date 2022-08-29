package com.example.smarttrader.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.smarttrader.R
import com.example.smarttrader.databinding.ActivityAccountCreationBinding
import com.example.smarttrader.fragments.RegisterFragment

class AccountCreation : AppCompatActivity() {


    private  lateinit var binding : ActivityAccountCreationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<RegisterFragment>(R.id.fragmentContainer)
            }
        }

    }
}