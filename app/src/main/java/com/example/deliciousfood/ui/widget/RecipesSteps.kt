

package com.example.deliciousfood.ui.widget

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deliciousfood.ui.theme.DeliciousFoodTheme

@Composable
fun RecipesStepsSelection(
    stepsCount: Int,
    modifier: Modifier = Modifier,
    selectedIndex: (Int) -> Unit
) {
    val steps = (1 until stepsCount + 1).toList()
    val currentIndex = remember { mutableStateOf(1) }
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp)
    ) {
        items(steps) { item ->
            val selected = item == currentIndex.value
            Surface(
                modifier = Modifier
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .padding(1.dp)
                    .clickable {
                        selectedIndex(item - 1)
                        currentIndex.value = item
                    },
                shape = getCurrentShape(item, steps),
                color = if (selected) Color.White else MaterialTheme.colorScheme.onBackground
            ) {
                Text(
                    modifier = Modifier.padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = 20.dp,
                        end = 20.dp
                    ),
                    text = "$item",
                    color = if (selected) Color.DarkGray else Color.White,
                )
            }
        }
    }
}

fun getCurrentShape(item: Int, steps: List<Int>): Shape {
    return when (item) {
        1 -> {
            RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
        }
        steps.size -> {
            RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)
        }
        else -> {
            RoundedCornerShape(0.dp)
        }
    }
}

@Preview("default")
@Preview("darkTheme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun PreviewRecipesSteps() {
    DeliciousFoodTheme {
        RecipesStepsSelection(stepsCount = 5 , selectedIndex = {})
    }
}
