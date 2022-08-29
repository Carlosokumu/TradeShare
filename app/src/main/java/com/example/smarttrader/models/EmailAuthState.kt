package com.example.smarttrader.models

sealed class EmailAuthState {



    data class  Success(val code: String): EmailAuthState()
    object  Loading: EmailAuthState()
    object  Error : EmailAuthState()
    /*
       *Based on the server code issued by the server,the email will
       * verified
     */
    data class  ServerCode(val code: Int?): EmailAuthState()
}
