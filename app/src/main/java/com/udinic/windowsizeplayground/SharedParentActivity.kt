package com.udinic.windowsizeplayground

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.udinic.windowsizeplayground.largescreens.LAYOUT_GROUPS_ALL_SAME
import com.udinic.windowsizeplayground.largescreens.LargeScreenUtil
import com.udinic.windowsizeplayground.largescreens.WindowHeightSizeClass
import com.udinic.windowsizeplayground.largescreens.WindowWidthSizeClass


abstract class SharedParentActivity : AppCompatActivity() {

    private val fixPauseResumeIssue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log("onCreate")
        LargeScreenUtil.updateCurrentWindowSize(this)
        printWindowMetrics()
    }

    fun printWindowMetrics() {
        val currentWindowDimensions = LargeScreenUtil.getCurrentWindowDimensions()
        val dimenDp = Pair(
            LargeScreenUtil.pxToDp(currentWindowDimensions.first),
            LargeScreenUtil.pxToDp(currentWindowDimensions.second))
        log("Cached window metrics updated: sizeClass${LargeScreenUtil.getCurrentWindowSizeClass()} (prev${LargeScreenUtil.getPreviousWindowSizeClass()}) | dimensDp${dimenDp} | dimensPx${LargeScreenUtil.getCurrentWindowDimensions()}")
    }

    protected abstract fun updateText()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        log("onConfigurationChanged")
        LargeScreenUtil.updateCurrentWindowSize(this)

        printWindowMetrics()
        updateText()

        if (LargeScreenUtil.isLayoutChangedForWindowResize(getSameLayoutGroups())) {
            log("onConfigurationChanged - RECREATING activity")
            recreate()
            return
        }
    }

    /**
     * List of size class groups, each group contains size classes that show the same layout
     * for this activity. See example in LargeScreenUtil.isLayoutChangedForWindowResize()
     */
    protected open fun getSameLayoutGroups(): List<List<Pair<WindowWidthSizeClass, WindowHeightSizeClass>>> {
        return LAYOUT_GROUPS_ALL_SAME
    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean, newConfig: Configuration?) {
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig)
        log( "onMultiWindowModeChanged")
    }

    var lastSizeClass: Pair<WindowWidthSizeClass, WindowHeightSizeClass>? = null

    override fun onPause() {
        super.onPause()
        log( "onPause")

        lastSizeClass = LargeScreenUtil.getCurrentWindowSizeClass()
    }

    override fun onResume() {
        super.onResume()
        log( "onResume")

        // If the size class was changed significantly since this activity was paused - we'll recreate the activity.
        // Can happen if we go from activity A -> B, resize to a size class with a different layout,
        // then come back from B -> A. We'll see the old layout for activity A, since it didn't get the onConfigurationChanged() callback
        lastSizeClass?.let {
            if (fixPauseResumeIssue && it != LargeScreenUtil.getCurrentWindowSizeClass()) {
                if (LargeScreenUtil.isLayoutChangedForWindowResize(getSameLayoutGroups(), it)) {
                    log("onResume - RECREATING activity curr[${LargeScreenUtil.getCurrentWindowSizeClass()}] <- lastSizeClass[${lastSizeClass}]")
                    recreate()
                    return
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        log( "onStart")
    }

    override fun onStop() {
        super.onStop()
        log( "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    protected fun log(message: String) {
        Log.d("WindowSizePlayground", "Activity[${javaClass.simpleName} | ${Integer.toHexString(hashCode())}] | $message")
    }
}
