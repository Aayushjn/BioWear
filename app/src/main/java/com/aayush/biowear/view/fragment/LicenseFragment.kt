package com.aayush.biowear.view.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.aayush.biowear.R

class LicenseFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogInflater = activity?.layoutInflater
        val licenseView = dialogInflater?.inflate(R.layout.fragment_license, null)

        return AlertDialog.Builder(context!!)
            .setView(licenseView)
            .setTitle("Open source licenses")
            .setNeutralButton("OK", null)
            .create()
    }

    companion object {
        fun newInstance() = LicenseFragment()
    }
}