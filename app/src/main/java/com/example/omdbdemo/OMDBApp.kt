package com.example.omdbdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class OMDBApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}