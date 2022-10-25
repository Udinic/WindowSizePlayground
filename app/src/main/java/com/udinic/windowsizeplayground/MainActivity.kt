package com.udinic.windowsizeplayground

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.snackbar.Snackbar
import com.udinic.windowsizeplayground.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val logtag = "WindowSizePlayground"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(logtag, "onCreate [API Version = ${Build.VERSION.SDK_INT}]")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.root.addView(object : View(this) {
            override fun onConfigurationChanged(newConfig: Configuration?) {
                super.onConfigurationChanged(newConfig)
                configChangeHandler("View", newConfig)
            }
        })
    }

    fun calcScreenDimen() {
        val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        Log.d(logtag, "Curr Dimens dp[${pxToDp(windowMetrics.bounds.width())}, ${pxToDp(windowMetrics.bounds.height())}] px[${windowMetrics.bounds.width()}, ${windowMetrics.bounds.height()}]. bounds = ${windowMetrics.bounds}")


//        val windowMetricsMax = WindowMetricsCalculator.getOrCreate().computeMaximumWindowMetrics(this)
//        Log.d(logtag, "Dimens [${windowMetricsMax.bounds.width()}, ${windowMetricsMax.bounds.height()}]. bounds = ${windowMetricsMax.bounds}")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configChangeHandler("Activity", newConfig)
    }

    fun configChangeHandler(caller: String, newConfig: Configuration?) {
        Log.d(logtag, "onConfigurationChanged caller[${caller}]")
        calcScreenDimen()
    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean, newConfig: Configuration?) {
        Log.d(logtag, "onConfigurationChanged")
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig)
    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
        Log.d(logtag, "onConfigurationChanged OLD")
        super.onMultiWindowModeChanged(isInMultiWindowMode)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun pxToDp(px: Int): Int {
        return pxToDp(px.toFloat())
    }
    fun pxToDp(px: Float): Int {
        return if (px > 0) (px / resources.displayMetrics.density + 0.5f).toInt()
        else -(-px / resources.displayMetrics.density + 0.5f).toInt()
    }
}
