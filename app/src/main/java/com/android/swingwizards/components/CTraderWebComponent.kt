package com.android.swingwizards.components

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CTraderWebComponent(onComplete: () -> Unit) {
    val mUrl = "https://openapi.ctrader.com/apps"
    var currentUrl by remember { mutableStateOf(mUrl) }
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    // Update the currentUrl when the page is finished loading
                    if (url != null) {
                        currentUrl = url
                        injectJavaScript(view!!)
                    }
                }
            }

            addJavascriptInterface(object : Any() {
                @JavascriptInterface
                fun onButtonClick(buttonId: String, clientId: String, clientSecret: String) {
                    // Log the button click event
                    Log.d("WebViewConsole", "Btn Clicked: $buttonId")
                    Log.d("WebViewConsole", "Input Value 1: $clientId")
                    Log.d("WebViewConsole", "Input Value 2: $clientSecret")
                    post {
                        when (buttonId) {
                            "btn-success" -> {
                                Toast.makeText(it.applicationContext, "Hey", Toast.LENGTH_LONG)
                                    .show()
                            }

                            "app-cred-btn" -> {
                                if (clientId == "" || clientSecret == "") {
                                    Toast.makeText(
                                        it.applicationContext,
                                        "All fields are required",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                            "tokens-btn" -> {
                                //add your action in here
                                Toast.makeText(
                                    it.applicationContext,
                                    "Tokens Btn Clicked",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }

                @JavascriptInterface
                fun onAccessTokens(buttonId: String, accessToken: String, refreshToken: String) {
                    Log.d("AccessToken", "Input Value 2: $accessToken")
                    Log.d("RefreshToken", "Input Value 2: $refreshToken")
                    onComplete()
                }

                @JavascriptInterface
                fun onEmptyField() {
                    Toast.makeText(
                        it.applicationContext,
                        "All fields are required",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }, "Android")
            loadUrl(mUrl)
        }
    }, update = {
        it.settings.javaScriptEnabled = true
        it.loadUrl(currentUrl)
        Log.d("URL:", currentUrl)
    })

}


fun injectJavaScript(webView: WebView) {

    Log.d("URLNOW:", webView.url.toString())
    var dynamicScript = ""

    when (webView.url.toString()) {
        "https://openapi.ctrader.com/apps" -> {
            dynamicScript = """
       
        var btnSuccessElement = document.getElementsByClassName('btn-success')[0];
        if (btnSuccessElement) {
            //btnSuccessElement.innerHTML = '<i class="glyphicon glyphicon-plus"></i> Modified Button';
            btnSuccessElement.addEventListener('click', function() {
                Android.onButtonClick('btn-success');
            });
        }
       // Create a bottom sheet div
        var bottomSheetDiv = document.createElement('div');
        bottomSheetDiv.id = 'bottomSheet';
        bottomSheetDiv.style.position = 'fixed';
        bottomSheetDiv.style.bottom = '0';
        bottomSheetDiv.style.left = '0';
        bottomSheetDiv.style.width = '100%';
        bottomSheetDiv.style.background = '#fff';
        bottomSheetDiv.style.borderTop = '1px solid #ccc';
        bottomSheetDiv.style.padding = '16px';
        bottomSheetDiv.style.boxShadow = '0px -4px 6px rgba(0, 0, 0, 0.1)';
        
        
           // Create content for the bottom sheet
        var content = document.createElement('p');
        content.textContent = 'Click on view to copy your client id and secret then Save';
        content.style.color = 'green';
        
        // Create the first input field
        var inputField1 = document.createElement('input');
        inputField1.type = 'text';
        inputField1.placeholder = 'Paste Client id  here';
        inputField1.className = 'form-control';
        inputField1.style.marginBottom = '16px';

        // Create the second input field
        var inputField2 = document.createElement('input');
        inputField2.type = 'text';
        inputField2.placeholder = 'Paste Secret Here';
        inputField2.className = 'form-control';
        inputField2.style.marginBottom = '16px';
        
     
        
        // Create a close button
        var closeButton = document.createElement('button');
        closeButton.textContent = 'Close';
        closeButton.style.marginTop = '8px';
        closeButton.onclick = function() {
            // Close the bottom sheet when the close button is clicked
            document.getElementById('bottomSheet').style.display = 'none';
        };

        // Create a second button
        var secondButton = document.createElement('button');
        secondButton.textContent = 'Save';
        secondButton.style.fontSize = '16px';
        secondButton.style.marginTop = '8px';
        secondButton.style.marginLeft = '8px';
        secondButton.style.backgroundColor = 'green';
        secondButton.onclick = function() {
            // Handle the click event for the second button
            var clientId = inputField1.value;
            var clientSecret = inputField2.value;
             if (clientId.trim() === '' || clientSecret.trim() === '') {
               Android.onEmptyField();
           } else {
              Android.onButtonClick("app-cred-btn", clientId,clientSecret);
              document.getElementById('bottomSheet').style.display = 'none';
           }
           
        };

        // Append input fields, content, buttons, and close button to the bottom sheet div
        bottomSheetDiv.appendChild(content);
        bottomSheetDiv.appendChild(inputField1);
        bottomSheetDiv.appendChild(inputField2);
        bottomSheetDiv.appendChild(closeButton);
        bottomSheetDiv.appendChild(secondButton);

        // Append the bottom sheet div to the body
        document.body.appendChild(bottomSheetDiv);
        
    """.trimIndent()
        }

        "https://openapi.ctrader.com/apps/6670/playground" -> {

            dynamicScript = """
       
        var btnSuccessElement = document.getElementsByClassName('btn-success')[0];
        if (btnSuccessElement) {
           // btnSuccessElement.innerHTML = '<i class="glyphicon glyphicon-plus"></i> Modified Button';
            btnSuccessElement.addEventListener('click', function() {
                Android.onButtonClick('btn-success');
            });
        }
       // Create a bottom sheet div
        var bottomSheetDiv = document.createElement('div');
        bottomSheetDiv.id = 'bottomSheet';
        bottomSheetDiv.style.position = 'fixed';
        bottomSheetDiv.style.bottom = '0';
        bottomSheetDiv.style.left = '0';
        bottomSheetDiv.style.width = '100%';
        bottomSheetDiv.style.background = '#fff';
        bottomSheetDiv.style.borderTop = '1px solid #ccc';
        bottomSheetDiv.style.padding = '16px';
        bottomSheetDiv.style.boxShadow = '0px -4px 6px rgba(0, 0, 0, 0.1)';
        
        // Create content for the bottom sheet
        var content = document.createElement('p');
        content.textContent = 'First,click getToken to get the most recent tokens,then copy and paste in the form below';
        content.style.color = 'green';
        
        // Create the first input field
        var inputField1 = document.createElement('input');
        inputField1.type = 'text';
        inputField1.placeholder = 'Paste your access token here';
        inputField1.className = 'form-control';
        inputField1.style.marginBottom = '16px';

        // Create the second input field
        var inputField2 = document.createElement('input');
        inputField2.type = 'text';
        inputField2.placeholder = 'Paste your refresh token here';
        inputField2.className = 'form-control';
        inputField2.style.marginBottom = '16px';
        
   
        
        // Create a close button
        var closeButton = document.createElement('button');
        closeButton.textContent = 'Close';
        closeButton.style.marginTop = '8px';
        closeButton.onclick = function() {
            // Close the bottom sheet when the close button is clicked
            document.getElementById('bottomSheet').style.display = 'none';
        };

        // Create a second button
        var secondButton = document.createElement('button');
        secondButton.textContent = 'Save';
        secondButton.style.marginTop = '8px';
        secondButton.style.fontSize = '16px';
        secondButton.style.marginLeft = '8px'; 
        secondButton.style.backgroundColor = 'green';
        secondButton.onclick = function() {
            
            var accessToken = inputField1.value;
            var refreshToken = inputField2.value;
            if (accessToken.trim() === '' || refreshToken.trim() === '') {
              Android.onEmptyField();
           } else {      
             Android.onAccessTokens("tokens-btn", accessToken,refreshToken);
             document.getElementById('bottomSheet').style.display = 'none';
             }
        };

        // Append input fields, content, buttons, and close button to the bottom sheet div
        bottomSheetDiv.appendChild(content);
        bottomSheetDiv.appendChild(inputField1);
        bottomSheetDiv.appendChild(inputField2);
  
        bottomSheetDiv.appendChild(closeButton);
        bottomSheetDiv.appendChild(secondButton);

        // Append the bottom sheet div to the body
        document.body.appendChild(bottomSheetDiv);
        
    """.trimIndent()

        }

        else -> {

        }
    }
    webView.evaluateJavascript(dynamicScript, null)


}