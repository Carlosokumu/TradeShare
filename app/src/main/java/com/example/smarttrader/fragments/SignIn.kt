package com.example.smarttrader.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.core.utils.Constants
import com.example.smarttrader.activities.AccountCreation
import com.example.smarttrader.activities.MainActivity
import com.example.smarttrader.databinding.FragmentSignInBinding
import com.example.smarttrader.models.LoginState
import com.example.smarttrader.settings.Settings
import com.example.smarttrader.showErrorToast
import com.example.smarttrader.showSnackBar
import com.example.smarttrader.showSuccessMessage
import com.example.smarttrader.showToast
import com.example.smarttrader.viewmodels.LoginViewModel
import com.example.smarttrader.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class SignIn : Fragment() {


    private lateinit var signInBinding: FragmentSignInBinding
    private lateinit var userName: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private val loginViewModel: LoginViewModel by viewModel()
    private val userViewModel: UserViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        signInBinding = FragmentSignInBinding.inflate(inflater, container, false)


        signInBinding.txtResetPass.setOnClickListener {
            //requireActivity().supportFragmentManager()
            ResetPass.newInstance().show(requireActivity().supportFragmentManager, "reset-password")
        }

        signInBinding.btnLogin.setOnClickListener {

            if (signInBinding.userName.text.isBlank()) {
               // signInBinding.layoutUserName.error = "please enter your username here"
                signInBinding.userName.error = "please enter your username here"

            } else {
                userName = signInBinding.userName.text.toString()

            }
            if (signInBinding.password.text.isBlank()) {
                signInBinding.password.error = "please enter your password here"
               // signInBinding.layoutPassword.error = "please enter your password here"
                return@setOnClickListener
            } else {
                password = signInBinding.password.text.toString()
            }



            loginViewModel.loginUser(userName, password)




        }
        lifecycleScope.launch {
            loginViewModel.uiState.collect { state ->
                when (state) {
                    is LoginState.Loading -> {
                        signInBinding.progressBar.visibility = View.VISIBLE
                        signInBinding.btnsignUp.alpha = 0.5f
                        signInBinding.btnsignUp.isEnabled = false
                    }
                    is LoginState.Success -> {
                        signInBinding.progressBar.visibility = View.INVISIBLE
                        Settings.setUserIsLogged(true)
                        Settings.setPassword(signInBinding.password.text.toString())
                        Settings.setUserName(signInBinding.userName.text.toString())
                        //Insert User into the Db.
                        userViewModel.insertUser(user = state.user)

                        requireContext().showSuccessMessage("Login successful")
                        Handler().postDelayed({ //This method will be executed once the timer is over
                            context?.startActivity(
                                Intent(
                                    requireActivity(),
                                    MainActivity::class.java
                                )
                            )
                            activity?.finish()
                        }, 1000)


                    }
                    is LoginState.Error -> {
                        signInBinding.progressBar.visibility = View.INVISIBLE
                        signInBinding.btnsignUp.alpha = 1f
                        signInBinding.btnsignUp.isEnabled = true
                       // requireContext().showToast(state.message)
                       // requireContext().showSnackBar(requireView())
                        requireContext().showErrorToast("Check your internet connection")
                    }
                    is LoginState.ServerCode -> {
                        signInBinding.progressBar.visibility = View.INVISIBLE
                        signInBinding.btnsignUp.alpha = 1f
                        signInBinding.btnsignUp.isEnabled = true
                        /*
                           * 403 -> Forbidden access when password  and username does not match
                           * 404 -> when the given username is not found
                         */


                        when (state.code) {
                            Constants.HTTP_404 -> {
                               // requireContext().showToast("Username does not exist")
                                requireContext().showErrorToast("Username does not exist")
                            }
                            Constants.HTTP_4BIDEN -> {
                               // requireContext().showToast("Your username and password does not match")
                                requireContext().showErrorToast("Your username and password does not match")
                            }
                            Constants.SERVICE_UNAVAILABLE ->{
                                Toast.makeText(requireContext(),"Service Unavailable",Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(requireContext(),state.code.toString(),Toast.LENGTH_SHORT).show()
                            }

                        }


                    }
                    is LoginState.Relaxed -> {
                        /*
                          No logic here
                         */
                    }
                }
            }


            // showDialog()
        }


        signInBinding.btnsignUp.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), AccountCreation::class.java))
        }



        return signInBinding.root
    }


}
