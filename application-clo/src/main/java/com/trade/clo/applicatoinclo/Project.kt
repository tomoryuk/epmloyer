package com.trade.clo.applicatoinclo

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import custom.lib.droid.flyer_extension.FlyerExtension
import custom.lib.droid.signal_extenstion.SignalExtension
import java.time.LocalDate


object Utils {
    fun implSignal(
        context: Context,
        value: String,
    ) {
        SignalExtension.setSignal(
            context = context,
            value = value
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun implApps(
        context: Context,
        value: String,
        startDate: LocalDate = LocalDate.of(2024, 1, 1),
        onError: () -> Unit,
        onSuccess: (FlyerModel) -> Unit
    ) {
        FlyerExtension.setExtension(
            context = context,
            value = value,
            startDate = startDate,
            onError = { onError() },
            onSuccess = { status ->
                onSuccess(status.convert())
            }
        )
    }

    data class FlyerModel(
        var status: FlyerStatus? = null,
        var content: String? = null,
    )

    enum class FlyerStatus {
        SUCCESS, ERROR
    }

    private fun FlyerExtension.FlyerModel.convert(): FlyerModel {
        return FlyerModel(
            status = this.status?.toFlyerModel(),
            content = this.content
        )
    }

    private fun FlyerExtension.FlyerStatus.toFlyerModel(): FlyerStatus? {
        return when (this) {
            custom.lib.droid.flyer_extension.FlyerExtension.FlyerStatus.SUCCESS -> FlyerStatus.SUCCESS
            custom.lib.droid.flyer_extension.FlyerExtension.FlyerStatus.ERROR -> FlyerStatus.ERROR
            else -> null
        }
    }

}

@Composable
fun CustomWebView(
    data: String
) {
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {

            settings.apply {
                javaScriptEnabled = true
                cacheMode = WebSettings.LOAD_NO_CACHE
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                allowFileAccess = true
                allowContentAccess = true
                javaScriptCanOpenWindowsAutomatically = true
                userAgentString = userAgents.random()
            }
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = MyWebViewClient()
            loadUrl(data)
        }
    }
    CookieManager.getInstance().setAcceptCookie(true)
    CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)

    AndroidView(
        factory = { webView },
        update = {
            it.loadUrl(data)
        },
        modifier = Modifier.fillMaxSize()
    )
}


internal class MyWebViewClient() : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view?.loadUrl(url!!)
        return false
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
    }
}


internal val userAgents = listOf(
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36",
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36",
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Firefox/96.0.1",
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Firefox/96.0.1",
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:95.0) Gecko/20100101 Firefox/95.0",
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Safari/537.36 Edg/96.0.1054.49",
    "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko",
    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36",
    "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:95.0) Gecko/20100101 Firefox/95.0",
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.2 Safari/605.1.15"
)