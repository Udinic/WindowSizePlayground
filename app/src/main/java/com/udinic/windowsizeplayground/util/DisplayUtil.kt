package com.udinic.windowsizeplayground.util

import android.app.Activity
import android.content.res.Resources
import android.util.Log
import androidx.window.layout.WindowMetricsCalculator

object DisplayUtil {
    var logtag = "DisplayUtil"
    var lastWidthSizeClass: WindowWidthSizeClass? = null

    /**
     * Update the cached dimens.
     * Returns a boolean whether the size class has changed
     */
    fun updateScreenDimens(activity: Activity): Boolean {
        val windowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
        val currWidthSizeClass = WindowWidthSizeClass.getWidthSizeClass(pxToDp(windowMetrics.bounds.width(), activity.resources))
        Log.d(
            logtag,
            "SizeClass[${currWidthSizeClass}] Dimens dp[${pxToDp(windowMetrics.bounds.width(), activity.resources)}, ${pxToDp(windowMetrics.bounds.height(), activity.resources)}] px[${windowMetrics.bounds.width()}, ${windowMetrics.bounds.height()}]. bounds = ${windowMetrics.bounds}"
        )

        if (lastWidthSizeClass == null) {
            lastWidthSizeClass = currWidthSizeClass
        } else if (currWidthSizeClass != lastWidthSizeClass) {
            Log.d(logtag, "size class updated [${lastWidthSizeClass}] -> [${currWidthSizeClass}].   ")
            lastWidthSizeClass = currWidthSizeClass
            return true
        }

        return false
    }

    fun pxToDp(px: Int, resources: Resources): Int {
        return pxToDp(px.toFloat(), resources)
    }
    fun pxToDp(px: Float, resources: Resources): Int {
        return if (px > 0) (px / resources.displayMetrics.density + 0.5f).toInt()
        else -(-px / resources.displayMetrics.density + 0.5f).toInt()
    }
}
