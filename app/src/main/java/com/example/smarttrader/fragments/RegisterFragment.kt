package com.example.smarttrader.fragments

import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.core.utils.Constants
import com.example.smarttrader.R
import com.example.smarttrader.data.local.mapper.toUser
import com.example.smarttrader.databinding.FragmentRegisterBinding
import com.example.smarttrader.models.AccountCreationState
import com.example.smarttrader.settings.Settings
import com.example.smarttrader.showErrorToast
import com.example.smarttrader.viewmodels.RegisterViewModel
import com.example.smarttrader.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ybs.passwordstrengthmeter.PasswordStrength
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class RegisterFragment : Fragment(), TextWatcher {
    private lateinit var registerBinding: FragmentRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private val registerViewModel: RegisterViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerBinding = FragmentRegisterBinding.inflate(inflater, container, false)


        registerBinding.btnsignUp.setOnClickListener {
            registerUser()
        }

        lifecycleScope.launch {
            registerViewModel.uiState.collect { creationState ->
                when (creationState) {
                    is AccountCreationState.ServerError -> {
                        Toast.makeText(
                            requireContext(),
                             creationState.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        when (creationState.code) {

                            Constants.SERVICE_UNAVAILABLE -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Service Unavailable",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    is AccountCreationState.Relaxed -> {

                    }
                    is AccountCreationState.Loading -> {
                        registerBinding.btnsignUp.text = "Signing up..."
                        registerBinding.btnsignUp.alpha = 0.5f
                        registerBinding.btnsignUp.isEnabled = false
                        registerBinding.progress.visibility = View.VISIBLE

                    }
                    is AccountCreationState.Success -> {
                        registerBinding.progress.visibility = View.GONE

                        val registeredUser = creationState.registeredUser
                        registerViewModel.saveUser(registeredUser.toUser())
                        val bundle = bundleOf(
                            "USERNAME" to registeredUser.user.username,
                            "EMAIL" to registeredUser.user.email
                        )

                        //Load data into the preference file
                        Settings.setUserIsLogged(true)
                        Settings.setUserName(registeredUser.user.username)
                        Settings.setPassword(registeredUser.user.password)


                        activity?.supportFragmentManager?.commit {
                            setReorderingAllowed(true)
                            setCustomAnimations(
                                R.anim.enter_fromright,
                                R.anim.exit_to_left,
                                R.anim.exit_to_left,
                                R.anim.enter_fromleft
                            )
                            replace<OtpFragment>(R.id.fragmentContainer, args = bundle)

                        }

                    }
                    is AccountCreationState.Error -> {
                        registerBinding.btnsignUp.text = "Sign Up"
                        registerBinding.btnsignUp.alpha = 1f
                        registerBinding.btnsignUp.isEnabled = true
                        requireActivity().showErrorToast("Check your internet connection")
                        registerBinding.progress.visibility = View.GONE


                    }
                }
            }
        }

        registerBinding.edPassword.addTextChangedListener(this)

        return registerBinding.root
    }


    private fun registerUser() {

        val firstName = registerBinding.edfirstName.text.toString()
        val lastName = registerBinding.edlastName.text.toString()
        val userName = registerBinding.edusername.text.toString()
        val email = registerBinding.edEmail.text.toString()
        val password = registerBinding.edPassword.text.toString()
        val confirmPassword = registerBinding.confrimPassword.text.toString()


        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(
                userName
            ) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
        ) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                registerBinding.edfirstName.error = "Please insert a valid email address"
                return
            }
            if (confirmPassword != password) {

                registerBinding.confrimPassword.error = "Password does not match"
                return
            }
            if (registerBinding.progressBarz.progress < 25) {

                registerBinding.edPassword.error =
                    "Make sure your password has 8 characters, at least a number and a symbol"
                return
            } else {

                registerViewModel.registerUser(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    userName = userName,
                    password = password,
                    context = requireContext()
                )


            }
        } else {


            if (registerBinding.edusername.text.isBlank()) {
                registerBinding.edusername.error = "enter your username here"
            }



            if (registerBinding.edfirstName.text.isBlank()) {
                registerBinding.edfirstName.error = "enter your first name here"
            }
            if (registerBinding.edlastName.text.isBlank()) {
                registerBinding.edlastName.error = "enter your last name here"
            }
            if (registerBinding.edEmail.text.isBlank()) {
                registerBinding.edEmail.error = "enter your email here"
            }
            if (registerBinding.edPassword.text.isBlank()) {
                registerBinding.edPassword.error = "enter your password here"
            }
            if (registerBinding.confrimPassword.text.isBlank()) {
                registerBinding.confrimPassword.error = "please confirm your password here"
            }
            //registerBinding.edPassword.error = "enter your password here"


            YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(1)
                .playOn(registerBinding.layoutEmail);


            YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(1)
                .playOn(registerBinding.layoutPassword);

            YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(1)
                .playOn(registerBinding.layoutUserName);

            YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(1)
                .playOn(registerBinding.signUpCardview);

            YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(1)
                .playOn(registerBinding.layoutfirstName);
            YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(1)
                .playOn(registerBinding.layoutlastName);

        }
    }


    private fun updatePasswordStrengthView(password: String) {
        val progressBar = registerBinding.progressBarz
        val strengthView = registerBinding.passwordStrength
        if (TextView.VISIBLE != strengthView.visibility) return
        if (password.isEmpty()) {
            progressBar.progressDrawable
                .setColorFilter(resources.getColor(R.color.transparent), PorterDuff.Mode.SRC_IN)
            strengthView.visibility = View.GONE
            strengthView.text = ""
            progressBar.progress = 0
            progressBar.visibility = View.GONE
            return
        }
        val str: PasswordStrength = PasswordStrength.calculateStrength(password)
        strengthView.text = str.getText(requireContext())
        strengthView.setTextColor(str.color)
        progressBar.progressDrawable.setColorFilter(str.color, PorterDuff.Mode.SRC_IN)
        if (str.getText(requireContext()).equals("")) {
            strengthView.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
        } else if (str.getText(requireContext()).equals("Weak")) {
            progressBar.visibility = View.VISIBLE
            strengthView.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            progressBar.progress = 25
        } else if (str.getText(requireContext()).equals("Medium")) {
            progressBar.progress = 50
        } else if (str.getText(requireContext()).equals("Strong")) {
            progressBar.progress = 75
        } else {
            progressBar.progress = 100
        }
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        updatePasswordStrengthView(s.toString())

    }

    override fun afterTextChanged(s: Editable?) {
        registerBinding.passwordStrength.visibility = View.VISIBLE
    }

}








