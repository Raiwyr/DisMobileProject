package com.example.dismobileproject.ui.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable

@Composable
fun logInUser(context: Context, userId: Int) {
    val sharedPref: SharedPreferences = context.getSharedPreferences(PreferenceNames.FAIL_NAME.name, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPref.edit()

    editor.putBoolean(PreferenceNames.IS_LOGGED.name, true)
    editor.putInt(PreferenceNames.USER_ID.name, userId)

    editor.apply()
}

@Composable
fun logOutUser(context: Context) {
    val sharedPref: SharedPreferences = context.getSharedPreferences(PreferenceNames.FAIL_NAME.name, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPref.edit()

    editor.putBoolean(PreferenceNames.IS_LOGGED.name, false)
    editor.putInt(PreferenceNames.USER_ID.name, -1)

    editor.apply()
}

@Composable
fun isUserLogged(context: Context): Boolean {
    val sharedPref: SharedPreferences = context.getSharedPreferences(PreferenceNames.FAIL_NAME.name, Context.MODE_PRIVATE)
    return sharedPref.getBoolean(PreferenceNames.IS_LOGGED.name, false)
}

@Composable
fun getLoggedUserId(context: Context): Int {
    val sharedPref: SharedPreferences = context.getSharedPreferences(PreferenceNames.FAIL_NAME.name, Context.MODE_PRIVATE)
    return sharedPref.getInt(PreferenceNames.USER_ID.name, -1)
}