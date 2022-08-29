package com.example.smarttrader.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.example.smarttrader.R
import com.example.smarttrader.databinding.FragmentWaitingBinding
import com.example.smarttrader.models.PhoneRegisterState
import com.example.smarttrader.viewmodels.PhoneRegister
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.seanghay.statusbar.statusBar
import ir.samanjafari.easycountdowntimer.CountDownInterface
import ir.samanjafari.easycountdowntimer.EasyCountDownTextview
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class WaitingFragment : Fragment() {
    private lateinit var waitingBinding: FragmentWaitingBinding
    private var ed1: EditText? = null
    private var ed2: EditText? = null
    private var ed3: EditText? = null
    private var ed4: EditText? = null
    private var ed5: EditText? = null
    private var ed6: EditText? = null
    private var phoneNumber: String? = null
    private lateinit var sentID: String
    lateinit var auth: FirebaseAuth
    private lateinit var userCode: String
    private var countDown: EasyCountDownTextview? = null
    private lateinit var databaseReference: DatabaseReference
    private lateinit var inputLayout: ViewGroup
    private lateinit var loadingIndicator: LottieAnimationView
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var userName: String
    private lateinit var email: String
    private val  phoneRegisterViewModel: PhoneRegister by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        phoneNumber = requireArguments().getString("phoneNumber")
        userName = requireArguments().getString("USERNAME")!!
        email = requireArguments().getString("EMAIL")!!



        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                val code = credential.smsCode
                if(code != null){
                    ed1!!.setText(code[0].toString())
                    ed2!!.setText(code[1].toString())
                    ed3!!.setText(code[2].toString())
                    ed4!!.setText(code[3].toString())
                    ed5!!.setText(code[4].toString())
                    ed6!!.setText(code[5].toString())
                    verifyCode(code)
                }

            }

            override fun onVerificationFailed(e: FirebaseException) {
                 Toast.makeText(requireContext(),e.message.toString(),Toast.LENGTH_SHORT).show()
                Log.d("ERRRRRRR",e.message.toString())
            }


            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                sentID = verificationId
                waitingBinding.vercodetext.text = "Code has been sent.Please verify..."
                inputLayout.visibility = View.VISIBLE
                countDown?.visibility = View.GONE
            }
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statusBar.color(color = resources.getColor(R.color.night)).light(true)
        waitingBinding = FragmentWaitingBinding.inflate(inflater, container, false)
        ed1 = waitingBinding.et1
        ed2 = waitingBinding.et2
        ed3 = waitingBinding.et3
        ed4 = waitingBinding.et4
        ed5 = waitingBinding.et5
        ed6 = waitingBinding.et6
        loadingIndicator = waitingBinding.progressIndicator
        countDown = waitingBinding.timertext
        inputLayout = waitingBinding.ver2
        setUpEdits()
        sendVerificationCode(phoneNumber!!)

        lifecycleScope.launch {
            phoneRegisterViewModel.uiState.collect { uploadState ->
                when(uploadState){
                    is PhoneRegisterState.Loading -> {

                    }
                    is PhoneRegisterState.Error -> {
                        Toast.makeText(requireContext(),"Something went while uploading your number",Toast.LENGTH_SHORT).show()
                    }
                    is PhoneRegisterState.Success ->{
                        val bundle = bundleOf("EMAIL" to email)
                        activity?.supportFragmentManager?.commit {
                            setReorderingAllowed(true)
                            replace<EmailAuth>(R.id.fragmentContainer,args = bundle)
                            setCustomAnimations(
                                R.anim.enter_fromright, R.anim.exit_to_left,R.anim.exit_to_left, R.anim.enter_fromleft
                            )

                        }
                        //requireActivity().startActivity(Intent(requireContext(),MainActivity::class.java))
                    }

                }

            }
        }

        return waitingBinding.root

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
                        loadingIndicator.visibility = View.VISIBLE
                        verifyCode(userCode)
                    } else {
                        loadingIndicator.visibility = View.GONE
                    }
                } else {
                    // ed1!!.requestFocus()
                }
            }

        })
    }

    private fun sendVerificationCode(number: String) {

        countDown?.visibility = View.VISIBLE
        countDown?.setTime(0, 0, 0, 60)
        countDown?.startTimer()
        countDown?.setOnTick(object : CountDownInterface {
            override fun onTick(time: Long) {

            }

            override fun onFinish() {
                countDown!!.visibility = View.GONE
            }

        })
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }


    private fun signWithCredentials(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if(task.isSuccessful){
                phoneRegisterViewModel.uploadPhoneNumber(userName = userName,phoneNumber = phoneNumber!!)
            }

        }
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(sentID, code)
        signWithCredentials(credential)
    }


}