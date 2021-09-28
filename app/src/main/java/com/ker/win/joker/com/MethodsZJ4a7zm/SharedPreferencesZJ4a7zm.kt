package com.ker.win.joker.com.MethodsZJ4a7zm

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPreferencesZJ4a7zm {

    private const val APP_PREFERENCES_TAG_ZJ4a7zm = "App_Preferences"
    private const val FIREBASE_DEFAULT_KEY_TAG_ZJ4a7zm = "Default_Key_Firebase"
    private const val LAST_PAGE_TAG_ZJ4a7zm = "Last_Page"

    fun putFirebaseDefaultKeyZJ4a7zm(firebaseKey : String, context: Context) {
        context.getSharedPreferences(APP_PREFERENCES_TAG_ZJ4a7zm, MODE_PRIVATE)
            .edit().putString(FIREBASE_DEFAULT_KEY_TAG_ZJ4a7zm, firebaseKey).apply()
    }
    fun getFirebaseDefaultKeyZJ4a7zm(context: Context) =
        context.getSharedPreferences(APP_PREFERENCES_TAG_ZJ4a7zm, MODE_PRIVATE)
            .getString(FIREBASE_DEFAULT_KEY_TAG_ZJ4a7zm, "null")

    fun putLastPageZJ4a7zm (lastPage: String, context: Context) {
        context.getSharedPreferences(APP_PREFERENCES_TAG_ZJ4a7zm, MODE_PRIVATE)
            .edit().putString(LAST_PAGE_TAG_ZJ4a7zm, lastPage).apply()
    }
    fun getLastPageZJ4a7zm (context: Context) =
        context.getSharedPreferences(APP_PREFERENCES_TAG_ZJ4a7zm, MODE_PRIVATE)
            .getString(LAST_PAGE_TAG_ZJ4a7zm, "null")



}