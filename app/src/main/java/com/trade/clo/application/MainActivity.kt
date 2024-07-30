package com.trade.clo.application

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.trade.clo.application.ui.theme.CLOV2Theme
import com.trade.clo.applicatoinclo.CustomWebView
import com.trade.clo.applicatoinclo.Utils

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Utils.implApps(
            this,
            value = "NUeb3YnY68xD7hzdfcUhzC",
            onError = { Log.e("TAG", "onCreate: On Error") },
            onSuccess = {
                Log.e(
                    "TAG",
                    "onCreate: success",
                )
            }
        )
        Utils.implSignal(this, value = "e21f8b99-643b-417c-bd06-9034d08b14cd")

        setContent {
            CLOV2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CustomWebView(data = "https://bussines-investquiz.com/bZ3Gh2")
                    innerPadding
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CLOV2Theme {
        Greeting("Android")
    }
}