package com.aayush.biowear.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aayush.biowear.R
import com.aayush.biowear.util.EXTRA_SOURCE
import com.aayush.biowear.util.TAG_DETAIL_FRAGMENT
import com.aayush.biowear.view.fragment.DetailFragment
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class DetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container,
                DetailFragment.newInstance(intent.getStringExtra(EXTRA_SOURCE)),
                TAG_DETAIL_FRAGMENT
            )
            .commit()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}
