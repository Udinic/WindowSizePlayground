package com.udinic.windowsizeplayground.largescreens
/**
 * These values are taken from
 * https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes#window_size_classes
 */
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
