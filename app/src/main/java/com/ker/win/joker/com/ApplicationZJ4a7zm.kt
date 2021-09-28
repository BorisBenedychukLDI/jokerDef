package com.ker.win.joker.com

import android.app.Application
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.ker.win.joker.com.MethodsZJ4a7zm.decodeBase64ZJ4a7zm
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicationZJ4a7zm : Application() {
    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.setAppId(decodeBase64ZJ4a7zm(BuildConfig.ONE_SIGNAL_KEY))
        val applsFlyerConversionZJ4a7zm = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(dataMapZJ4a7zm: MutableMap<String, Any>?) {
                dataMapZJ4a7zm?.run {

                    map {
                        Log.d("TEST_1_APPS", "conversion_attr: ${it.key}: ${it.value}")
                    }

                    statusZJ4a7zm =
                        if (getValue(APPSFLYER_STATUS_TAG_ZJ4a7zm).toString() == "Organic") "Organic" else "Non-organic"
                    Log.d("TEST_APPS", getValue(APPSFLYER_STATUS_TAG_ZJ4a7zm).toString())

                    val paramsArray = mutableListOf<String>()
                    getValue(APPSFLYER_CAMPAIGN_TAG_ZJ4a7zm)
                        .toString()
                        .split("||").drop(1)
                        .map {
                            paramsArray.add(it.split(":")[1])
                        }

                    pS1ZJ4a7zm = if (paramsArray.size >= 1) paramsArray[0] else EMPTY_TAG_ZJ4a7zm
                    pS2ZJ4a7zm = if (paramsArray.size >= 2) paramsArray[1] else EMPTY_TAG_ZJ4a7zm
                    pS3ZJ4a7zm = if (paramsArray.size >= 3) paramsArray[2] else EMPTY_TAG_ZJ4a7zm

                    adGroupZJ4a7zm = getValue(APPSFLYER_ADGROUP_TAG_ZJ4a7zm).toString()
                    adSet2ZJ4a7zm = getValue(APPSFLYER_ADSET_TAG_ZJ4a7zm).toString()
                    mediaSourceZJ4a7zm = getValue(APPSFLYER_MEDIA_SOURCE_TAG_ZJ4a7zm).toString()
                    afChannelZJ4a7zm = getValue(APPSFLYER_AF_CHANNEL_TAG_ZJ4a7zm).toString()


                }
            }

            override fun onConversionDataFail(p0: String?) {
            }

            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
            }

            override fun onAttributionFailure(p0: String?) {
            }
        }
        AppsFlyerLib.getInstance().run {
            APPSFLYER_UIDZJ4a7zm = getAppsFlyerUID(this@ApplicationZJ4a7zm)
            init(
                decodeBase64ZJ4a7zm(BuildConfig.APPS_FLYER_KEY),
                applsFlyerConversionZJ4a7zm,
                this@ApplicationZJ4a7zm
            )
            start(this@ApplicationZJ4a7zm)
        }
        MobileAds.initialize(this)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                GAIDZJ4a7zm =
                    AdvertisingIdClient.getAdvertisingIdInfo(this@ApplicationZJ4a7zm)?.id
                Log.d("TEST_1_GAID", GAIDZJ4a7zm.toString())
            } catch (e: Exception) {

            }
        }
    }


    companion object {
        var pS1ZJ4a7zm: String? = null
        var pS2ZJ4a7zm: String? = null
        var pS3ZJ4a7zm: String? = null

        var adGroupZJ4a7zm: String? = null
        var adSet2ZJ4a7zm: String? = null
        var afChannelZJ4a7zm: String? = null
        var mediaSourceZJ4a7zm: String? = null

        var GAIDZJ4a7zm: String? = null
        var APPSFLYER_UIDZJ4a7zm: String? = null

        var statusZJ4a7zm: String? = null

        const val EMPTY_TAG_ZJ4a7zm = "empty"


        private const val APPSFLYER_CAMPAIGN_TAG_ZJ4a7zm = "campaign"
        private const val APPSFLYER_STATUS_TAG_ZJ4a7zm = "af_status"
        private const val APPSFLYER_ADGROUP_TAG_ZJ4a7zm = "adgroup"
        private const val APPSFLYER_ADSET_TAG_ZJ4a7zm = "adset"
        private const val APPSFLYER_AF_CHANNEL_TAG_ZJ4a7zm = "af_channel"
        private const val APPSFLYER_MEDIA_SOURCE_TAG_ZJ4a7zm = "media_source"


    }
}