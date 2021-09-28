package com.ker.win.joker.com.MethodsZJ4a7zm

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPreferencesZJ4a7zm {

    private const val APP_PREFERENCES_TAG_ZJ4a7zm = "App_Preferences"
    private const val FIREBASE_DEFAULT_KEY_TAG_ZJ4a7zm = "Default_Key_Firebase"
    private const val LAST_PAGE_TAG_ZJ4a7zm = "Last_Page"

    fun putFirebaseDefaultKeyZJ4a7zm(firebaseKeyZJ4a7zm : String, contextZJ4a7zm: Context) {
        contextZJ4a7zm.getSharedPreferences(APP_PREFERENCES_TAG_ZJ4a7zm, MODE_PRIVATE)
            .edit().putString(FIREBASE_DEFAULT_KEY_TAG_ZJ4a7zm, firebaseKeyZJ4a7zm).apply()
    }
    fun getFirebaseDefaultKeyZJ4a7zm(contextZJ4a7zm: Context) =
        contextZJ4a7zm.getSharedPreferences(APP_PREFERENCES_TAG_ZJ4a7zm, MODE_PRIVATE)
            .getString(FIREBASE_DEFAULT_KEY_TAG_ZJ4a7zm, "null")

    fun putLastPageZJ4a7zm (lastPageZJ4a7zm: String, contextZJ4a7zm: Context) {
        contextZJ4a7zm.getSharedPreferences(APP_PREFERENCES_TAG_ZJ4a7zm, MODE_PRIVATE)
            .edit().putString(LAST_PAGE_TAG_ZJ4a7zm, lastPageZJ4a7zm).apply()
    }
    fun getLastPageZJ4a7zm (contextZJ4a7zm: Context) =
        contextZJ4a7zm.getSharedPreferences(APP_PREFERENCES_TAG_ZJ4a7zm, MODE_PRIVATE)
            .getString(LAST_PAGE_TAG_ZJ4a7zm, "null")



}