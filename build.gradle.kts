// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
//    alias(libs.plugins.hiltDagger) apply false
//    alias(libs.plugins.safearg) apply false

//    id("com.android.application") version "8.3.2" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
//    id("com.android.library") version "8.3.2" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
}