package com.example.cinemax.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemax.ui.theme.BlueAccent
import com.example.cinemax.ui.theme.CinemaxTheme
import com.example.cinemax.ui.theme.Dark
import com.example.cinemax.ui.theme.Soft
import com.example.cinemax.ui.theme.White
import com.example.core.ui.components.ShimmerEffect

@Composable
fun MovieGenresShimmer(){
    Column {
        ShimmerEffect(
            modifier = Modifier.height(45.dp).width(100.dp).clip(RoundedCornerShape(10.dp))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieGenresCard(
    category : String,
    selectedCategory: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = category == selectedCategory,
        onClick = {
            onSelected(category)
        },
        label = {
            Text(text = category,
                fontSize = 14.sp
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Soft,
            selectedLabelColor = BlueAccent,
            containerColor = Soft,
            labelColor = Color.White
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = Dark,
            selectedBorderColor = Soft,
            enabled = true,
            selected = true
        ),
        modifier = modifier.height(40.dp)
    )
}

@Composable
fun MovieCategoryTabs(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val selectedIndex = categories.indexOf(selectedCategory)

    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 16.dp, // Optional: adds padding at the start and end
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                color = Color.Cyan // Customize indicator color if needed
            )
        },
        containerColor = Dark, // Customize background color
        contentColor = White // Customize the content color for text
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onCategorySelected(category) }, // Trigger ViewModel update on click
                selectedContentColor = Soft,
                unselectedContentColor = Dark
            ) {
                Text(
                    text = category,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieGenreCardPreview() {
    CinemaxTheme {
        MovieCategoryTabs(
            categories = listOf("Action", "Adventure", "Animation"),
            selectedCategory = "Action",
            onCategorySelected = {}
        )
    }
}