package com.example.facebookintegrationapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.facebookintegrationapp.ui.theme.FacebookIntegrationAppTheme
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult


class MainActivity : ComponentActivity() {

    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callbackManager = CallbackManager.Factory.create()

        val loginManager = LoginManager.getInstance()

        loginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK)
        loginManager.registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Toast.makeText(this@MainActivity, result.toString(), Toast.LENGTH_LONG)
                        .show()
                    Log.d("tags", result.toString())
                }

                override fun onCancel() {
                    Toast.makeText(this@MainActivity, "Cancel", Toast.LENGTH_LONG).show()
                    Log.d("tags", "Cancel")
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(this@MainActivity, error.toString(), Toast.LENGTH_LONG)
                        .show()
                    Log.d("tags", error.toString())
                }
            })

        setContent {
            FacebookIntegrationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Button(
                            modifier = Modifier.wrapContentSize(),
                            onClick = {
                                LoginManager.getInstance()
                                    .logInWithReadPermissions(
                                        this@MainActivity,
                                        listOf("public_profile")
                                    )
                            }) {
                            Text(text = "Facebook")
                        }
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FacebookIntegrationAppTheme {
        Greeting("Android")
    }
}