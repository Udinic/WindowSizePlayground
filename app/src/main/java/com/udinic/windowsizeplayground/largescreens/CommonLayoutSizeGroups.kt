package com.udinic.windowsizeplayground.largescreens

val LAYOUT_GROUPS_W600 =
    listOf(
        listOf(
            Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.COMPACT),
            Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.MEDIUM),
            Pair(WindowWidthSizeClass.MEDIUM, WindowHeightSizeClass.COMPACT),
            Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.EXPANDED),
            Pair(WindowWidthSizeClass.EXPANDED, WindowHeightSizeClass.COMPACT),
        ),
        listOf(
            Pair(WindowWidthSizeClass.MEDIUM, WindowHeightSizeClass.MEDIUM),
            Pair(WindowWidthSizeClass.MEDIUM, WindowHeightSizeClass.EXPANDED),
            Pair(WindowWidthSizeClass.EXPANDED, WindowHeightSizeClass.MEDIUM),
            Pair(WindowWidthSizeClass.EXPANDED, WindowHeightSizeClass.EXPANDED),
        )
    )

val LAYOUT_GROUPS_W600_W840 =
    listOf(
        listOf(
            Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.COMPACT),
            Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.MEDIUM),
            Pair(WindowWidthSizeClass.MEDIUM, WindowHeightSizeClass.COMPACT),
            Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.EXPANDED),
            Pair(WindowWidthSizeClass.EXPANDED, WindowHeightSizeClass.COMPACT),
        ),
        listOf(
            Pair(WindowWidthSizeClass.MEDIUM, WindowHeightSizeClass.MEDIUM),
            Pair(WindowWidthSizeClass.MEDIUM, WindowHeightSizeClass.EXPANDED),
        ),
        listOf(
            Pair(WindowWidthSizeClass.EXPANDED, WindowHeightSizeClass.MEDIUM),
            Pair(WindowWidthSizeClass.EXPANDED, WindowHeightSizeClass.EXPANDED),
        )
    )

val LAYOUT_GROUPS_ALL_SAME =
    listOf(
        listOf(
            Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.COMPACT),
            Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.MEDIUM),
            Pair(WindowWidthSizeClass.MEDIUM, WindowHeightSizeClass.COMPACT),
            Pair(WindowWidthSizeClass.COMPACT, WindowHeightSizeClass.EXPANDED),
            Pair(WindowWidthSizeClass.EXPANDED, WindowHeightSizeClass.COMPACT),
            Pair(WindowWidthSizeClass.MEDIUM, WindowHeightSizeClass.MEDIUM),
            Pair(WindowWidthSizeClass.MEDIUM, WindowHeightSizeClass.EXPANDED),
            Pair(WindowWidthSizeClass.EXPANDED, WindowHeightSizeClass.MEDIUM),
            Pair(WindowWidthSizeClass.EXPANDED, WindowHeightSizeClass.EXPANDED),
        )
    )

