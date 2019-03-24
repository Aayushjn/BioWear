plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.aayush.biowear"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.21")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")

    implementation("androidx.appcompat:appcompat:1.1.0-alpha03")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-alpha3")
    implementation("androidx.core:core-ktx:1.1.0-alpha05")

    implementation("com.github.bumptech.glide:glide:4.8.0")
    kapt("com.github.bumptech.glide:compiler:4.8.0")

    implementation("com.github.CardinalNow:Android-CircleProgressIndicator:v0.2")

    implementation("com.github.AnyChart:AnyChart-Android:1.1.2")

    implementation("com.google.android.gms:play-services-auth:16.0.1")
    implementation("com.google.android.material:material:1.1.0-alpha04")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("com.google.firebase:firebase-core:16.0.7")

    implementation("com.jakewharton.timber:timber:4.7.1")

    implementation("com.wdullaer:materialdatetimepicker:4.1.2")

    implementation("io.github.inflationx:calligraphy3:3.0.0")
    implementation("io.github.inflationx:viewpump:1.0.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.0")

    androidTestImplementation("androidx.test:runner:1.1.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")
}
