package com.aayush.biowear.util

import android.content.SharedPreferences
import com.aayush.biowear.model.User
import com.google.gson.Gson

fun isUserRegistered(sharedPreferences: SharedPreferences) = sharedPreferences.getBoolean(
    PREF_IS_REGISTERED,
    false
)

fun registerUser(sharedPreferences: SharedPreferences, isRegistered: Boolean, user: User) {
    val json = Gson().toJson(user)
    sharedPreferences
        .edit()
        .putString(PREF_USER, json)
        .apply()

    sharedPreferences
        .edit()
        .putBoolean(PREF_IS_REGISTERED, isRegistered)
        .apply()
}

fun getRegisteredUser(sharedPreferences: SharedPreferences) =
    Gson().fromJson<User>(sharedPreferences.getString(PREF_USER, ""), User::class.java)
