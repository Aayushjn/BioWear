package com.aayush.biowear.view.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.aayush.biowear.GlideApp
import com.aayush.biowear.R
import com.aayush.biowear.util.*
import com.aayush.biowear.view.activity.DetailActivity
import com.aayush.biowear.view.activity.ProfileActivity
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber


class MainFragment: Fragment() {
    private var steps = 31264
    private var totalSteps = 20000
    private var heartRate = 73
    private var temperature = 36f
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = getRegisteredUser(PreferenceManager.getDefaultSharedPreferences(context!!)).name

        GlideApp.with(this)
            .asGif()
            .load(LINK_HEART_RATE_GIF)
            .into(heartRateImageView)

        setupListeners()

        userChip.text = String.format(getString(R.string.greeting), name)

        heartRateTextView.text = String.format(getString(R.string.format_heart_rate), heartRate)
        stepsProgress.interpolator = AccelerateDecelerateInterpolator()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stepsProgress.setProgress(steps, true)
        }
        else {
            stepsProgress.progress = steps
        }
        stepCountTextView.text = steps.toString()
        totalStepsTextView.text = String.format(getString(R.string.format_steps), totalSteps)

        thermometerView.setProgress(temperature)
        when {
            temperature <= TEMP_HYPOTHERMIA -> temperatureTextView.text = getString(R.string.info_hypothermia)
            temperature >= TEMP_FEVER -> temperatureTextView.text = getString(R.string.info_fever)
            else -> temperatureTextView.text = getString(R.string.info_normal)
        }
    }

    private fun setupListeners() {
        userChip.setOnClickListener {
            val intent = Intent(context!!, ProfileActivity::class.java)
            startActivity(intent)
        }

        menuImageButton.setOnClickListener {
            showMenu(it)
        }

        val detailIntent = Intent(context!!, DetailActivity::class.java)
        stepsCardView.setOnClickListener {
            detailIntent.putExtra(EXTRA_SOURCE, "Steps")
            startActivity(detailIntent)
        }
        heartRateCardView.setOnClickListener {
            detailIntent.putExtra(EXTRA_SOURCE, "HeartRate")
            startActivity(detailIntent)
        }
        temperatureCardView.setOnClickListener {
            detailIntent.putExtra(EXTRA_SOURCE, "Temperature")
            startActivity(detailIntent)
        }
    }

    private fun showMenu(view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.menu_button, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_license -> {
                    LicenseFragment.newInstance()
                        .show(activity?.supportFragmentManager?.beginTransaction()!!, TAG_LICENSE_FRAGMENT)
                    true
                }
                else -> {
                    Timber.wtf("How could this even happen")
                    false
                }
            }
        }
        popupMenu.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
