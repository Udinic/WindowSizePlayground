package com.udinic.windowsizeplayground.util

/**
 * These values are taken from
 * https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes#window_size_classes
 */
enum class WindowWidthSizeClass {
    COMPACT,
    MEDIUM,
    EXPANDED;
    companion object {
        fun getWidthSizeClass(widthDp: Int): WindowWidthSizeClass {
            if (widthDp < 0) {
                throw IllegalArgumentException("Width cannot be negative!")
            }
            return when (widthDp) {
                in 0 until 600 -> COMPACT
                in 600 until 840 -> MEDIUM
                else -> EXPANDED
            }
        }
    }
}
