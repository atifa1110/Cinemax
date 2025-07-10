package com.example.cinemax.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.cinemax.ui.theme.BlueAccent
import kotlin.math.absoluteValue
import kotlin.math.sign

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DefaultHorizontalPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color = BlueAccent,
    inactiveColor: Color = activeColor.copy(PagerIndicatorInactiveColorAlpha),
    activeIndicatorWidth: Dp = 16.dp,
    indicatorWidth: Dp = 8.dp,
    indicatorHeight: Dp = indicatorWidth,
    spacing: Dp = indicatorWidth,
    indicatorShape: Shape = CircleShape,
) {
    val indicatorWidthPx = LocalDensity.current.run { indicatorWidth.roundToPx() }
    val spacingPx = LocalDensity.current.run { spacing.roundToPx() }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { page ->
                val horizontalPadding by animateDpAsState(
                    targetValue = if (page == pagerState.currentPage) spacing else 0.dp, label = ""
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = horizontalPadding)
                        .size(width = indicatorWidth, height = indicatorHeight)
                        .background(color = inactiveColor, shape = indicatorShape)
                )
            }
        }

        Box(
            Modifier
                .offset {
                    val position = pageIndexMapping(pagerState.currentPage)
                    val offset = pagerState.currentPageOffsetFraction
                    val next = pageIndexMapping(pagerState.currentPage + offset.sign.toInt())
                    val scrollPosition = ((next - position) * offset.absoluteValue + position)
                        .coerceIn(
                            minimumValue = 0f,
                            maximumValue = (pageCount - 1)
                                .coerceAtLeast(minimumValue = 0)
                                .toFloat()
                        )

                    IntOffset(
                        x = ((spacingPx + indicatorWidthPx) * scrollPosition).toInt(),
                        y = 0
                    )
                }
                .size(width = activeIndicatorWidth, height = indicatorHeight)
                .background(color = activeColor, shape = indicatorShape)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.pagerTransition(
    pagerState: PagerState,
    page: Int
) = graphicsLayer {
    val pageOffset = pagerState.calculatePageOffset(page)

    lerp(
        start = 0.85f,
        stop = 1f,
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    ).also { scale ->
        scaleX = scale
        scaleY = scale
    }

    alpha = lerp(
        start = 0.5f,
        stop = 1f,
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    )
}

@OptIn(ExperimentalFoundationApi::class)
private fun PagerState.calculatePageOffset(page: Int) =
    ((currentPage - page) + currentPageOffsetFraction).absoluteValue

private const val PagerIndicatorInactiveColorAlpha = 0.32f