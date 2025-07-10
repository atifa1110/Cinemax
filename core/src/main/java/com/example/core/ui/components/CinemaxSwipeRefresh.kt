package com.example.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import com.example.core.ui.theme.Soft
import com.example.core.ui.theme.BlueAccent
import com.example.core.ui.theme.Dark

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CinemaxSwipeRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Soft,
    contentColor: Color = BlueAccent,
    indicatorPadding: PaddingValues = PaddingValues(0.dp),
    indicatorAlignment: Alignment = Alignment.TopCenter,
    scale: Boolean = true,
    content: @Composable () -> Unit
) {
    val state = rememberPullRefreshState(refreshing = isRefreshing,
        onRefresh = onRefresh
    )
    Box(modifier =  modifier.fillMaxSize()
        .background(Dark).testTag("SwipeRefresh") // ✅ Add testTag for swipe refresh
        .pullRefresh(state = state)) {
        content()

        PullRefreshIndicator(
            modifier = Modifier
                .padding(indicatorPadding)
                .align(indicatorAlignment).testTag("RefreshIndicator"), // ✅ Add testTag for loading indicator,
            refreshing = isRefreshing,
            state = state,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            scale = scale
        )
    }
}