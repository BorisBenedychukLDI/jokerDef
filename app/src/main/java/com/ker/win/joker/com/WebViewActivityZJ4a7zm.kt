package com.ker.win.joker.com

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.ker.win.joker.com.MethodsZJ4a7zm.SharedPreferencesZJ4a7zm
import com.ker.win.joker.com.MethodsZJ4a7zm.checkPermissionsZJ4a7zm
import com.ker.win.joker.com.databinding.ActivityWebViewZj4a7zmBinding
import kotlinx.coroutines.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class WebViewActivityZJ4a7zm : AppCompatActivity() {

    private var uriViewZJ4a7zm = Uri.EMPTY
    private var filePathCallBackZJ4a7zm: ValueCallback<Array<Uri>>? = null
    private var internetCheckJobZJ4a7zm: Job? = null
    private var internetCheckDialogZJ4a7zm: InternetCheckDialogZJ4a7zm? = null


    private lateinit var bindingZJ4a7zm: ActivityWebViewZj4a7zmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingZJ4a7zm = ActivityWebViewZj4a7zmBinding.inflate(layoutInflater)
        setContentView(bindingZJ4a7zm.root)
        bindingZJ4a7zm.root.setOnRefreshListener {
            bindingZJ4a7zm.wvUrlZj4a7zm.loadUrl(bindingZJ4a7zm.wvUrlZj4a7zm.url ?: return@setOnRefreshListener)
            bindingZJ4a7zm.root.isRefreshing = false
        }
        bindingZJ4a7zm.wvUrlZj4a7zm.run {

            val cookiesManagerZJ4a7zm = CookieManager.getInstance()
            CookieManager.setAcceptFileSchemeCookies(true)
            cookiesManagerZJ4a7zm.setAcceptThirdPartyCookies(this, true)

            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            settings.run {
                defaultTextEncodingName = "utf-8"
                builtInZoomControls = true
                displayZoomControls = false
                builtInZoomControls = true
                domStorageEnabled = true
                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                javaScriptEnabled = true
                useWideViewPort = true
                mediaPlaybackRequiresUserGesture = false

            }
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    viewZJ4a7zm: WebView?,
                    requestZJ4a7zm: WebResourceRequest?
                ): Boolean {

                    val modifiedLinksZJ4a7zm = listOf("mailto:", "tel:")
                    val prohibitedLinksZJ4a7zm =
                        listOf("facebook", "currents.google.com")
                    return when {
                        modifiedLinksZJ4a7zm.find {
                            requestZJ4a7zm?.url.toString().startsWith(it)
                        } != null -> {
                            viewZJ4a7zm?.context?.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    requestZJ4a7zm?.url
                                )
                            )
                            true
                        }
                        prohibitedLinksZJ4a7zm.find {
                            requestZJ4a7zm?.url.toString().contains(it)
                        } != null -> {
                            true
                        }
                        else -> false
                    }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    SharedPreferencesZJ4a7zm.putLastPageZJ4a7zm(
                        url.toString(),
                        this@WebViewActivityZJ4a7zm
                    )
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(
                    webViewZJ4a7zm: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParamsZJ4a7zm: FileChooserParams?
                ): Boolean {
                    checkPermissionsZJ4a7zm(this@WebViewActivityZJ4a7zm)
                    filePathCallBackZJ4a7zm = filePathCallback
                    val captureIntentZJ4a7zm = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (captureIntentZJ4a7zm.resolveActivity(this@WebViewActivityZJ4a7zm.packageManager) != null) {
                        val providedFileZJ4a7zm = createTempFileZJ4a7zm()
                        uriViewZJ4a7zm = FileProvider.getUriForFile(
                            this@WebViewActivityZJ4a7zm,
                            "${this@WebViewActivityZJ4a7zm.application.packageName}.provider",
                            providedFileZJ4a7zm
                        )
                        captureIntentZJ4a7zm.run {
                            putExtra(MediaStore.EXTRA_OUTPUT, uriViewZJ4a7zm)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        val actionIntentZJ4a7zm = Intent(Intent.ACTION_GET_CONTENT).apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "image/*"
                        }
                        val intentChooserZJ4a7zm = Intent(Intent.ACTION_CHOOSER).apply {
                            putExtra(Intent.EXTRA_INTENT, captureIntentZJ4a7zm)
                            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(actionIntentZJ4a7zm))
                        }
                        startActivityForResult(intentChooserZJ4a7zm, 0)
                        return true
                    }
                    return super.onShowFileChooser(
                        webViewZJ4a7zm,
                        filePathCallback,
                        fileChooserParamsZJ4a7zm
                    )
                }
            }
            if (SharedPreferencesZJ4a7zm.getLastPageZJ4a7zm(this@WebViewActivityZJ4a7zm) != "null") {
                loadUrl(SharedPreferencesZJ4a7zm.getLastPageZJ4a7zm(this@WebViewActivityZJ4a7zm)!!)
            } else {
                loadUrl(intent.getStringExtra("webViewURL") ?: return@run)
            }
        }
    }


    override fun onActivityResult(requestCodeZJ4a7zm: Int, resultCode: Int, data: Intent?) {
        if (requestCodeZJ4a7zm == 0) {
            filePathCallBackZJ4a7zm?.let {
                val uriResultZJ4a7zm =
                    if (data == null || resultCode != RESULT_OK) null else data.data
                if (uriResultZJ4a7zm != null) {
                    filePathCallBackZJ4a7zm?.onReceiveValue(arrayOf(uriResultZJ4a7zm))
                } else {
                    filePathCallBackZJ4a7zm?.onReceiveValue(arrayOf(uriViewZJ4a7zm))
                }
                filePathCallBackZJ4a7zm = null
            }
            super.onActivityResult(requestCodeZJ4a7zm, resultCode, data)
        }
    }

    override fun onBackPressed() {
        when {
            bindingZJ4a7zm.wvUrlZj4a7zm.canGoBack() && bindingZJ4a7zm.wvUrlZj4a7zm.isFocused -> bindingZJ4a7zm.wvUrlZj4a7zm.goBack()
            else -> super.onBackPressed()
        }
    }

    fun createTempFileZJ4a7zm(): File {
        val dateZJ4a7zm = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileDirZJ4a7zm = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("TMP${dateZJ4a7zm}_ZJ4a7zm", ".jpg", fileDirZJ4a7zm)
    }

    fun isNetworkPresentZJ4a7zm(): Boolean {
        val connectivityManagerZJ4a7zm =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilitiesZJ4a7zm = connectivityManagerZJ4a7zm
                .getNetworkCapabilities(connectivityManagerZJ4a7zm.activeNetwork) ?: return false
            return networkCapabilitiesZJ4a7zm.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            for (networkZJ4a7zm in connectivityManagerZJ4a7zm.allNetworks) {
                connectivityManagerZJ4a7zm.getNetworkInfo(networkZJ4a7zm)?.let {
                    if (it.isConnected) return true
                }
            }
            return false
        }
    }

    override fun onResume() {
        internetCheckJobZJ4a7zm = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                if (!isNetworkPresentZJ4a7zm()) {
                    withContext(Dispatchers.Main) {
                        if (internetCheckDialogZJ4a7zm == null) {
                            internetCheckDialogZJ4a7zm = InternetCheckDialogZJ4a7zm().apply {
                                show(supportFragmentManager, "Internet_Checker")
                            }
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        internetCheckDialogZJ4a7zm?.dismiss()
                        internetCheckDialogZJ4a7zm = null
                    }
                }
                delay(500)
            }
        }
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        internetCheckJobZJ4a7zm?.cancel()
    }
}