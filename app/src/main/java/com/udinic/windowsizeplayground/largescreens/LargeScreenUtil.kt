package com.udinic.windowsizeplayground.largescreens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.hardware.display.DisplayManager
import android.util.DisplayMetrics
import android.view.Display
import androidx.window.layout.WindowMetricsCalculator
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Helper class to identify large screen devices and enable adaptive layouts or features relevant to
 * large screens only.
 */
object LargeScreenUtil {
  const val MIN_VALID_DENSITY = 100
  const val SIZE_INCH_MULTIPLIER = 100

  private var density = 1f

  private var currWindowDimensionsPx: Pair<Int, Int> = Pair(-1, -1)
  private var currWindowSizeClass: Pair<WindowWidthSizeClass, WindowHeightSizeClass> =
    Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.COMPACT)
  private var prevWindowSizeClass: Pair<WindowWidthSizeClass, WindowHeightSizeClass> =
    Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.COMPACT)

  /**
   * Calculates the current window dimensions.
   *
   * @return Pair<Int,Int> Pair of numbers, representing the window's width and height
   * (respectively) in pixels
   */
  @JvmStatic
  fun getCurrentWindowDimensions(activity: Activity): Pair<Int, Int> {
    val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
    val width = windowMetrics.bounds.width()
    val height = windowMetrics.bounds.height()

    // Updating density, which will be useful for calculations
    density = activity.resources.displayMetrics.density
    return Pair(width, height)
  }

  /**
   * Calculates the current window size class.
   *
   * @return Pair<WindowWidthSizeClass, WindowHeightSizeClass> Pair of size class values of the
   * window's width and height (respectively)
   */
  @JvmStatic
  fun getCurrentWindowSizeClass(
    activity: Activity
  ): Pair<WindowWidthSizeClass, WindowHeightSizeClass> {
    val windowDimensions = getCurrentWindowDimensions(activity)
    val widthSizeClass = WindowWidthSizeClass.getWidthSizeClass(pxToDp(windowDimensions.first))
    val heightSizeClass = WindowHeightSizeClass.getHeightSizeClass(pxToDp(windowDimensions.second))
    return Pair(widthSizeClass, heightSizeClass)
  }

  /**
   * Retrieves the cached window size classes. The cached values should be kept updated by calling
   * updateCurrentWindowSize(Activity).
   *
   * For best results, use getCurrentWindowSizeClass(Activity), which calculates these values for
   * the current Activity.
   * @return Pair<WindowWidthSizeClass, WindowHeightSizeClass> The width and height size classes.
   */
  @Deprecated(
    "This method uses cached values, prefer calling getCurrentWindowSizeClass(Activity) for the most up-to-date values",
    replaceWith = ReplaceWith("getCurrentWindowSizeClass(activity)"))
  @JvmStatic
  fun getCurrentWindowSizeClass(): Pair<WindowWidthSizeClass, WindowHeightSizeClass> {
    return currWindowSizeClass
  }

  /**
   * Retrieves the cached window dimensions. The cached values should be kept updated by calling
   * updateCurrentWindowSize(Activity).
   *
   * For best results, use getCurrentWindowDimensions(Activity), which calculates these values for
   * the current Activity.
   * @return Pair<Int, Int> The width and height size in pixels.
   */
  @Deprecated(
    "This method uses cached values, prefer calling getCurrentWindowDimensions(Activity) for the most up-to-date values",
    replaceWith = ReplaceWith("getCurrentWindowDimensions(activity)"))
  @JvmStatic
  fun getCurrentWindowDimensions(): Pair<Int, Int> {
    return currWindowDimensionsPx
  }

  /**
   * Retrieves the previous window size class, prior to the last time the window was resized. Note:
   * The previous size class can be the same as the current size class if there were no significant
   * changes the last time the cache was updated.
   *
   * @return Pair<WindowWidthSizeClass, WindowHeightSizeClass> The previous width and height size
   * classes.
   */
  @JvmStatic
  fun getPreviousWindowSizeClass(): Pair<WindowWidthSizeClass, WindowHeightSizeClass> {
    return prevWindowSizeClass
  }

  /**
   * Check if the available display area fits the required size class. The available area is
   * retrieved from the Activity. Example: If the device is a regular phone and we asked for a
   * minimum of WindowWidthSizeClass.MEDIUM, then this method returns "false".
   */
  @JvmStatic
  fun isWindowSizeClassAtLeast(
    minWidthSizeClass: WindowWidthSizeClass,
    minHeightSizeClass: WindowHeightSizeClass,
    activity: Activity
  ): Boolean {
    val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
    val height = windowMetrics.bounds.height()
    val width = windowMetrics.bounds.width()

    return isWindowSizeClassAtLeast(
      minWidthSizeClass, minHeightSizeClass, pxToDp(width), pxToDp(height))
  }

  /**
   * Check if the available display area fits the required size class. Example: If the device is a
   * regular phone and we asked for a minimum of WindowWidthSizeClass.MEDIUM, then this method
   * returns "false".
   */
  @JvmStatic
  fun isWindowSizeClassAtLeast(
    minWidthSizeClass: WindowWidthSizeClass,
    minHeightSizeClass: WindowHeightSizeClass,
    windowWidth: Int,
    windowHeight: Int,
  ): Boolean {
    val currWidthSizeClass = WindowWidthSizeClass.getWidthSizeClass(windowWidth)
    val currHeightSizeClass = WindowHeightSizeClass.getHeightSizeClass(windowHeight)

    return (currWidthSizeClass >= minWidthSizeClass) && (currHeightSizeClass >= minHeightSizeClass)
  }

  /**
   * Retrieves and stores the current window's dimensions, also updating the current width and
   * height size classes accordingly.
   */
  @JvmStatic
  fun updateCurrentWindowSize(activity: Activity) {
    val windowDimensions = getCurrentWindowDimensions(activity)
    currWindowDimensionsPx = Pair(windowDimensions.first, windowDimensions.second)

    prevWindowSizeClass = currWindowSizeClass
    currWindowSizeClass =
      Pair(
        WindowWidthSizeClass.getWidthSizeClass(pxToDp(windowDimensions.first)),
        WindowHeightSizeClass.getHeightSizeClass(pxToDp(windowDimensions.second)))
  }

  /**
   * High level gating for the fullscreen features, gating based on experimentation state and device
   * type. In order to also check screen size compatibility - use shouldShowLargeScreenFeatures()
   * instead.
   */
  private fun isLargeScreenFeaturesAllowed(context: Context): Boolean {
      val minPhysicalScreenSize = 700
      if (minPhysicalScreenSize > 0) {
        val physicalScreenDimensions = getPhysicalScreenDimensions(context)
        val diagonalSize = physicalScreenDimensions?.diagonalInches ?: 0

        val allowedBySize =
          diagonalSize > minPhysicalScreenSize
        return allowedBySize
      }
    return false
  }

  /**
   * Calculates whether the most recent window resize requires changing the layout, which would mean
   * recreating the Activity. This should be called within onConfigurationChanged() after calling
   * LargeScreenUtil.updateCurrentWindowSize(), but also in other lifecycle events that would need
   * to compare the current size class with a previous size class in order to decide if restarting
   * the activity is necessary.
   *
   * The method accepts an array / vararg of groups, each group contains Pairs of size classes
   * (Width and Height) that use the same layout. A transition between size classes within the same
   * group means that the same layout would be used and there's no need to recreate the activity. A
   * transition between size classes in different groups means the UI needs to change its layout and
   * therefore we need to recreate the activity. The same-layout groups would need to be defined by
   * the developer of the Activity, who will decide which size classes would result with the same
   * layout, based on window size resource qualifiers for XML layouts and the logic that constructs
   * the layout in the Activity.
   *
   * Example: Group1 contains [(COMPACT, COMPACT), (COMPACT,MEDIUM),...] Group2 contains [(MEDIUM,
   * MEDIUM), (MEDIUM_EXPANDED),...] So if in the last window resize we transitioned from
   * (COMPACT,COMPACT) to (COMPACT, MEDIUM) - we won't need to recreate the activity, but
   * transitioning from (COMPACT, COMPACT) to (MEDIUM, MEDIUM) will, because according to the groups
   * definition - this new size class has a different layout than the previous one.
   *
   * See the unit tests of this class for more examples.
   */
  @JvmStatic
  fun isLayoutChangedForWindowResize(
    sameLayoutSizeClassGroups: List<List<Pair<WindowWidthSizeClass, WindowHeightSizeClass>>>?,
    prevWindowSizeClass: Pair<WindowWidthSizeClass, WindowHeightSizeClass> =
      getPreviousWindowSizeClass(),
  ): Boolean {
    // No groups definition means we don't need to act on layout changes.
    if (sameLayoutSizeClassGroups == null || sameLayoutSizeClassGroups.isEmpty()) {
      return false
    }

    assertLayoutGroupsCoverAllPermutations(sameLayoutSizeClassGroups)

    val elements = listOf(prevWindowSizeClass, getCurrentWindowSizeClass())
    for (currGroup in sameLayoutSizeClassGroups) {
      // If there's a group that contains both previous and current size classes - then we don't
      // need to recreate the activity.
      if (currGroup.containsAll(elements)) {
        return false
      }
    }
    return true
  }

  /** Verify the "same layout groups" cover all different size class permutations. */
  private fun assertLayoutGroupsCoverAllPermutations(
    sameLayoutSizeClassGroups: List<List<Pair<WindowWidthSizeClass, WindowHeightSizeClass>>>
  ) {
    val numScenariosCovered = sameLayoutSizeClassGroups.toList().flatten().distinct().size
    val numTotalScenarios = WindowWidthSizeClass.values().size * WindowHeightSizeClass.values().size

    if (numScenariosCovered != numTotalScenarios) {
      throw IllegalArgumentException(
        "Asserting size class groups failed - not all scenarios were covered. " +
                "Need $numTotalScenarios but got $numScenariosCovered. " +
                "Covered scenarios: ${sameLayoutSizeClassGroups.toList().flatten().distinct()}")
    }
  }

  /**
   * Returns a physical screen dimension (inches x 100) of the device default display (based on OEM
   * provided screen resolution and ppi info) or null if this info is missing or clearly wrong.
   * Note: For UI layout purposes, use getCurrentWindowSizeClass() or getCurrentWindowDimensions()
   */
  @JvmStatic
  @SuppressLint("DeprecatedMethod")
  fun getPhysicalScreenDimensions(context: Context): PhysicalScreenDimensions? {
    val displayService = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    val display = displayService?.getDisplay(Display.DEFAULT_DISPLAY)
    val dm = DisplayMetrics()

    // While getRealMetrics() is deprecated, this is the only way right now to get the screen size
    // in inches.
    display?.getRealMetrics(dm)

    // return null if we get garbage from OEM populated fields
    if (dm.xdpi < MIN_VALID_DENSITY ||
      dm.ydpi < MIN_VALID_DENSITY ||
      dm.widthPixels <= 0 ||
      dm.heightPixels <= 0) {
      return null
    }

    val width = dm.widthPixels * SIZE_INCH_MULTIPLIER / dm.xdpi
    val height = dm.heightPixels * SIZE_INCH_MULTIPLIER / dm.ydpi
    val diagonal = sqrt(width.toDouble().pow(2.0) + height.toDouble().pow(2.0))

    return PhysicalScreenDimensions(width.toInt(), height.toInt(), diagonal.toInt())
  }

  /**
   * Converts pixel value to dp. Uses the cached density value from the last window metrics
   * calculations.
   */
  public fun pxToDp(px: Int): Int {
    return if (px > 0) (px / density + 0.5f).toInt() else -(-px / density + 0.5f).toInt()
  }
}
