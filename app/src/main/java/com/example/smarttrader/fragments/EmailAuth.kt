package com.example.smarttrader.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.example.smarttrader.R
import com.example.smarttrader.activities.MainActivity
import com.example.smarttrader.models.EmailAuthState
import com.example.smarttrader.settings.Settings
import com.example.smarttrader.showSnackBar
import com.example.smarttrader.viewmodels.EmailAuthViewModel
import ir.samanjafari.easycountdowntimer.CountDownInterface
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class EmailAuth : Fragment() {

    private val  authVm: EmailAuthViewModel by viewModel()
    private lateinit var email: String
    private lateinit var userCode: String


    //Views
    private var ed1: EditText? = null
    private var ed2: EditText? = null
    private var ed3: EditText? = null
    private var ed4: EditText? = null
    private var ed5: EditText? = null
    private var ed6: EditText? = null
    private var  loadingIndicator: LottieAnimationView?=null
    private lateinit var  timerText: ir.samanjafari.easycountdowntimer.EasyCountDownTextview
    private lateinit var  layoutEmail: LinearLayout
    private lateinit var txtUpdate: TextView
    private lateinit var resend: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = requireArguments().getString("EMAIL")!!


        /**

           * Send  a 6 digit code[handled by the server] to the provided email in the onCreate callback
           * of this Fragment

         */

        authVm.sendEmail(email)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootie = inflater.inflate(R.layout.fragment_email_auth, container, false)

        layoutEmail = rootie.findViewById(R.id.layoutEmail)
        timerText = rootie.findViewById(R.id.timerText)

        ed1 = rootie.findViewById(R.id.et1)
        ed2 = rootie.findViewById(R.id.et2)
        ed3 = rootie.findViewById(R.id.et3)
        ed4 = rootie.findViewById(R.id.et4)
        ed5 = rootie.findViewById(R.id.et5)
        ed6 = rootie.findViewById(R.id.et6)
        loadingIndicator = rootie.findViewById(R.id.progress_indicator)
        txtUpdate = rootie.findViewById(R.id.txtUpdate)
        resend = rootie.findViewById(R.id.txtResend)


        resend.setOnClickListener {
            authVm.sendEmail(email)
            sendEmail()
        }

        sendEmail()
        setUpEdits()

        return rootie
    }

    companion object {


    }
    private fun setUpEdits() {
        ed1?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                if (text?.length == 1) {
                    ed2?.requestFocus()
                } else {
                    ed1!!.clearFocus()
                }
            }

        })
        ed2?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                if (text?.length == 1) {
                    ed3?.requestFocus()
                } else {
                    ed1!!.requestFocus()
                }
            }

        })
        ed3?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                if (text?.length == 1) {
                    ed4?.requestFocus()
                } else {
                    ed2!!.requestFocus()
                }

            }

        })
        ed4?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                if (text?.length == 1) {
                    ed5?.requestFocus()
                } else {
                    ed3!!.requestFocus()
                }
            }

        })
        ed5?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                if (text?.length == 1) {
                    ed6?.requestFocus()
                } else {
                    ed4!!.requestFocus()
                }
            }

        })
        ed6?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                if (text?.length == 1) {
                    ed6!!.clearFocus()
                    if (ed1?.text?.trim().toString().length == 1 && ed2?.text?.trim()
                            .toString().length == 1 && ed3?.text?.trim()
                            .toString().length == 1 && ed4?.text?.trim()
                            .toString().length == 1 && ed5?.text?.trim()
                            .toString().length == 1 && ed6?.text?.trim().toString().length == 1
                    ) {
                        userCode = ed1?.text.toString() + ed2?.text.toString() + ed3?.text.toString() + ed4?.text.toString() + ed5?.text.toString() + ed6?.text.toString()
                        //Verify the code after user input
                        verifyCode(code = userCode)

                    } else {
                        loadingIndicator?.visibility = View.GONE
                    }
                } else {
                    // ed1!!.requestFocus()
                }
            }

        })
    }


    private fun verifyCode(code: String){
        val serverOtpCode = Settings.getOtp()
        if (code == serverOtpCode){
            //if email verified -> Launch mainActivity
            Settings.setEmailVerified(true)
            Settings.setActiveEmail(email)


            requireActivity().startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
        else {
            txtUpdate.setTextColor(resources.getColor(R.color.red))
            txtUpdate.text = "Invalid Code"
        }
    }


    private fun sendEmail(){
        lifecycleScope.launch {
            authVm.uiState.collect { emailAuthState ->
                when (emailAuthState) {
                    is EmailAuthState.Loading -> {
                        timerText.visibility = View.VISIBLE
                        timerText.startTimer()

                        loadingIndicator?.visibility = View.VISIBLE

                        timerText.setOnTick(object : CountDownInterface {
                            override fun onTick(time: Long) {

                            }

                            override fun onFinish() {
                                resend.visibility = View.VISIBLE
                                timerText.visibility = View.GONE
                            }

                        })
                    }
                    is EmailAuthState.Error -> {
                        timerText.visibility = View.GONE
                        loadingIndicator?.visibility = View.GONE
                        resend.visibility = View.VISIBLE
                        requireContext().showSnackBar(requireView())
                    }
                    is EmailAuthState.Success -> {
                        loadingIndicator?.visibility = View.GONE
                        timerText.visibility = View.GONE
                        layoutEmail.visibility = View.VISIBLE
                        txtUpdate.text = "Email has been sent to $email "


                        val otpCode = emailAuthState.code
                        Settings.setOtpCode(otpCode)

                    }
                    is EmailAuthState.ServerCode ->{
                        Toast.makeText(requireContext(),
                            emailAuthState.code.toString(),Toast.LENGTH_SHORT).show()
                        timerText.visibility = View.GONE
                        loadingIndicator?.visibility = View.GONE
                    }
                }
            }
        }
    }

}