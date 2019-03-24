package com.aayush.biowear.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aayush.biowear.R
import com.aayush.biowear.view.fragment.ProfileFragment
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ProfileFragment.newInstance())
            .commit()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}
