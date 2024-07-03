package com.android.swingwizards.settings

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.core.utils.Constants

object Settings {


    private var defaultPreferences: SharedPreferences? =null
    private const val SHARED_PREFERENCE_NAME: String = "Smart-trader"

    /*
       This will be used to collect data in key Value pairs from our sheets(Haha(*~ *))
     */
    var dataPreferences: SharedPreferences?=null
    /*
        This will be used to initialize the the preferences
     */
    fun init(context: Context){
        defaultPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        dataPreferences =context.getSharedPreferences(SHARED_PREFERENCE_NAME,0)
    }


    fun setUserIsLogged(loggedIn: Boolean){
        dataPreferences?.edit()?.putBoolean("LOGGED",loggedIn)?.apply()
    }


    fun isFirstTimeLaunch(): Boolean?{
        return dataPreferences?.getBoolean("LAUNCH",true)
    }
    fun setFirstTimeLaunch(launch: Boolean){
        dataPreferences?.edit()?.putBoolean("LAUNCH",launch)?.apply()
    }


     fun isUserLogged(): Boolean?{
        return dataPreferences?.getBoolean("LOGGED",false)
    }

    fun setUserName(userName: String) = dataPreferences?.edit()?.putString("USERNAME",userName)?.apply()

    fun getUserName() = dataPreferences?.getString("USERNAME",null)


    fun setUserExit(exit: Boolean) = dataPreferences?.edit()?.putBoolean("EXIT",exit)?.apply()



    fun hasUserExited () = dataPreferences?.getBoolean("EXIT",false)


    fun setOtpCode(code: String) = dataPreferences?.edit()?.putString(Constants.OPT_CODE,code)?.apply()



    fun getOtp() = dataPreferences?.getString(Constants.OPT_CODE,null)



    fun setPassword(password: String) = dataPreferences?.edit()?.putString(Constants.PASSWORD,password)?.apply()


    fun getPassword() = dataPreferences?.getString(Constants.PASSWORD,null)



    fun setEmailVerified(isVerified: Boolean) = dataPreferences?.edit()?.putBoolean(Constants.EMAIL_VERIFIED,isVerified)?.apply()

    fun isEmailVerified() = dataPreferences?.getBoolean(Constants.EMAIL_VERIFIED,false)


    fun setActiveEmail(email: String) = dataPreferences?.edit()?.putString(Constants.EMAIL,email)?.apply()

    fun getEmail() = dataPreferences?.getString(Constants.EMAIL,null)

    fun setActiveUsername(userName: String) = dataPreferences?.edit()?.putString(Constants.USERNAME,userName)?.apply()
    fun getActiveUsername() = dataPreferences?.getString(Constants.USERNAME,null)


}