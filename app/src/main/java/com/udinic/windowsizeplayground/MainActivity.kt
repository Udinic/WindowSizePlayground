package com.udinic.windowsizeplayground

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.snackbar.Snackbar
import com.udinic.windowsizeplayground.databinding.ActivityMainBinding
import com.udinic.windowsizeplayground.util.DisplayUtil


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val logtag = "WindowSizePlayground"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(logtag, "onCreate [API Version = ${Build.VERSION.SDK_INT}]")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.addView(object : View(this) {
            override fun onConfigurationChanged(newConfig: Configuration?) {
                super.onConfigurationChanged(newConfig)
                configChangeHandler("View", newConfig)
            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configChangeHandler("Activity", newConfig)
    }

    fun configChangeHandler(caller: String, newConfig: Configuration?) {
        Log.d(logtag, "onConfigurationChanged caller[${caller}]")
        val shouldRecreateActivity = DisplayUtil.updateScreenDimens(this)
        if (shouldRecreateActivity) {
            recreate()
        }
    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean, newConfig: Configuration?) {
        Log.d(logtag, "onMultiWindowModeChanged")
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig)
    }

    fun pxToDp(px: Int): Int {
        return pxToDp(px.toFloat())
    }
    fun pxToDp(px: Float): Int {
        return if (px > 0) (px / resources.displayMetrics.density + 0.5f).toInt()
        else -(-px / resources.displayMetrics.density + 0.5f).toInt()
    }
}
