package com.aayush.biowear.util

fun getBmiCategory(age: Int, bmi: Float): String {
    return if (age >= 18) {
        if (bmi < 16f) {
            "Severe Thinness"
        } else if (bmi >= 16f && bmi < 17f) {
            "Moderate Thinness"
        } else if (bmi >= 17f && bmi < 18.5f) {
            "Mild Thinness"
        } else if (bmi >= 18.5f && bmi < 25f) {
            "Normal"
        } else if (bmi >= 25f && bmi < 30f) {
            "Overweight"
        } else if (bmi >= 30f && bmi < 35f) {
            "Obese Class I"
        } else if (bmi >= 35f && bmi < 40f) {
            "Obese Class II"
        }
        else {
            "Obese Class III"
        }
    }
    else {
        "BMI not calculated for ages less than 18"
    }
}