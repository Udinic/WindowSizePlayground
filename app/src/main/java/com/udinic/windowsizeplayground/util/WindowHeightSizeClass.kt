package com.udinic.windowsizeplayground.util

enum class WindowHeightSizeClass {
    COMPACT,
    MEDIUM,
    EXPANDED;
    companion object {
        fun getHeightSizeClass(heightDp: Int): WindowHeightSizeClass {
            if (heightDp < 0) {
                throw IllegalArgumentException("Height cannot be negative!")
            }
            return when (heightDp) {
                in 0 until 480 -> COMPACT
                in 480 until 900 -> MEDIUM
                else -> EXPANDED
            }
        }
    }
}
