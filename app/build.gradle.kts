import java.util.Properties


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.tmmovie"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tmmovie"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val keystoreFile = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())
        val apiKey = properties.getProperty("API_KEY") ?: ""
        buildConfigField("String", "API_KEY", apiKey)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kapt {
        correctErrorTypes = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)


    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.rxjava)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.adapter.rxjava)

    implementation(libs.hilt.android)
    kapt(libs.hilt.complier)

    implementation(libs.glide)

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}