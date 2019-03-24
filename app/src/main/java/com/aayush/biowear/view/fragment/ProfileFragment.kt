package com.aayush.biowear.view.fragment

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aayush.biowear.R
import com.aayush.biowear.model.Gender
import com.aayush.biowear.model.User
import com.aayush.biowear.util.FORMAT_DATE
import com.aayush.biowear.util.TAG_DATE_PICKER_DIALOG_FRAGMENT
import com.aayush.biowear.util.getRegisteredUser
import com.aayush.biowear.util.registerUser
import com.aayush.biowear.view.activity.MainActivity
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_profile.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment: Fragment(), DatePickerDialog.OnDateSetListener {
    private var name: String? = null
    private var birthday: Date? = null
    private var height: Float? = null
    private var weight: Float? = null
    private var gender: Gender? = null
    private var emergencyContact: String? = null
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = getRegisteredUser(PreferenceManager.getDefaultSharedPreferences(context!!))
        name = user?.name
        gender = user?.gender
        birthday = user?.birthday
        height = user?.height
        weight = user?.weight
        emergencyContact = user?.emergencyContact

        nameEditText.setText(name)
        genderEditText.setText(gender.toString().toLowerCase().capitalize())
        birthdayEditText.setText(SimpleDateFormat(FORMAT_DATE, Locale.US).format(birthday))
        heightEditText.setText(height.toString())
        weightEditText.setText(weight.toString())
        emergencyContactEditText.setText(emergencyContact)
        bmiEditText.setText(String.format("%.2f", user?.bmi))

        val calendar = Calendar.getInstance()
        calendar.time = birthday
        birthdayEditText.setOnClickListener {
            Toast.makeText(context!!, "User must be at least 10 years old", Toast.LENGTH_SHORT).show()
            val datePickerDialog = DatePickerDialog.newInstance(
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            calendar.set(Calendar.YEAR, 2009)
            datePickerDialog.maxDate = calendar
            datePickerDialog.accentColor = ContextCompat.getColor(context!!, R.color.appGreen)
            datePickerDialog.showYearPickerFirst(true)
            datePickerDialog.show(activity?.supportFragmentManager!!, TAG_DATE_PICKER_DIALOG_FRAGMENT)
        }

        updateButton.setOnClickListener {
            if (nameEditText.length() <= 0) {
                nameEditText.error = "Field can't be empty!"
            }
            else {
                name = nameEditText.text.toString()
            }

            if (heightEditText.length() <= 0) {
                heightEditText.error = "Field can't be empty!"
            }
            else {
                height = heightEditText.text.toString().toFloat()
            }

            if (weightEditText.length() <= 0) {
                weightEditText.error = "Field can't be empty!"
            }
            else {
                weight = weightEditText.text.toString().toFloat()
            }

            gender = Gender.valueOf(genderEditText.text.toString().toUpperCase())

            if (emergencyContactEditText.length() <= 0) {
                emergencyContactEditText.error = "Field can't be empty!"
            }
            else {
                emergencyContact = emergencyContactEditText.text.toString()
            }

            if (name != null && birthday != null && height != null && weight != null && gender != null &&
                emergencyContact != null) {
                user = User(name!!, birthday!!, height!!, weight!!, gender!!, emergencyContact!!)
                Timber.d(user.toString())
                registerUser(PreferenceManager.getDefaultSharedPreferences(context!!), true, user!!)

                val intent = Intent(context!!, MainActivity::class.java)
                startActivity(intent)
                activity?.finishAffinity()
            }
        }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, monthOfYear, dayOfMonth)
        birthday = calendar.time
        birthdayEditText.setText(SimpleDateFormat(FORMAT_DATE, Locale.US).format(birthday))
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}