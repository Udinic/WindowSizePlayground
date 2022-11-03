package com.udinic.windowsizeplayground

import android.app.Application
import android.os.Build
import android.util.Log

class WindowPlaygroundApplication : Application()
{
    override fun onCreate() {
        super.onCreate()
        Log.d("WindowSizePlayground", "Started WindowSizePlayground. SDK [${Build.VERSION.SDK_INT}]")
    }
}
