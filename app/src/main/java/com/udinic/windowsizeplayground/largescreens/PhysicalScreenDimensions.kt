package com.udinic.windowsizeplayground.largescreens

data class PhysicalScreenDimensions(
    /** Width in inches x 100 */
    val widthInches: Int,
    /** Height in inches x 100 */
    val heightInches: Int,
    /** Diagonal size in inches x 100 */
    val diagonalInches: Int
)
