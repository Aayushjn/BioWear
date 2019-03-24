package com.aayush.biowear.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.aayush.biowear.R
import com.aayush.biowear.util.TAG_MAIN_FRAGMENT
import com.aayush.biowear.util.TAG_REGISTER_FRAGMENT
import com.aayush.biowear.util.isUserRegistered
import com.aayush.biowear.view.fragment.MainFragment
import com.aayush.biowear.view.fragment.RegisterFragment
import io.github.inflationx.viewpump.ViewPumpContextWrapper


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val transaction = supportFragmentManager.beginTransaction()
        if (isUserRegistered(sharedPreferences)) {
            transaction
                .replace(R.id.fragment_container, MainFragment.newInstance(), TAG_MAIN_FRAGMENT)
                .commit()
        }
        else {
            transaction
                .add(R.id.fragment_container, RegisterFragment.newInstance(), TAG_REGISTER_FRAGMENT)
                .commit()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}
