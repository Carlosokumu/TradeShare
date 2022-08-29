package com.example.smarttrader.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.smarttrader.R
import com.example.smarttrader.databinding.FragmentOtpBinding
import com.hbb20.CountryCodePicker
import com.seanghay.statusbar.statusBar


class OtpFragment : Fragment(), CountryCodePicker.OnCountryChangeListener {


    private lateinit var fragmentOtpBinding: FragmentOtpBinding
    private lateinit var phoneNumber: String
    private lateinit var ccp: CountryCodePicker
    private lateinit var countrycode: String
    private lateinit var fullNumber: String
    private lateinit var userName: String
    private lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        statusBar.color(color = resources.getColor(R.color.night)).light(true)
        fragmentOtpBinding = FragmentOtpBinding.inflate(layoutInflater, container, false)
        ccp = fragmentOtpBinding.ccp
        countrycode = ccp.selectedCountryCodeWithPlus
        ccp.setOnCountryChangeListener(this)

        userName = requireArguments().getString("USERNAME")!!
        email = requireArguments().getString("EMAIL")!!

        fragmentOtpBinding.genOtp.setOnClickListener {
            if (fragmentOtpBinding.mobileNumber.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please enter a valid number", Toast.LENGTH_SHORT)
                    .show()
            } else {
                phoneNumber = fragmentOtpBinding.mobileNumber.text.trim().toString()
                fullNumber = phoneNumber.replace(" ", "")
                fullNumber = countrycode + fullNumber
                val bundle = bundleOf(PHONENUMBER to fullNumber,"USERNAME" to userName,"EMAIL" to email)
                activity?.supportFragmentManager?.commit {
                    setReorderingAllowed(true)
                    replace<WaitingFragment>(R.id.fragmentContainer,args = bundle)
                    setCustomAnimations(
                        R.anim.enter_fromright, R.anim.exit_to_left,R.anim.exit_to_left, R.anim.enter_fromleft
                    )

                }
            }

        }

        return fragmentOtpBinding.root
    }


    companion object {
        internal const val PHONENUMBER = "phoneNumber"
    }

    override fun onCountrySelected() {
        countrycode = ccp.selectedCountryCodeWithPlus
    }


}