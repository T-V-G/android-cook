
package com.example.deliciousfood.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.skydoves.landscapist.coil.CoilImage
import com.example.deliciousfood.domain.model.RecipesItem
import com.example.deliciousfood.ui.common.IconFavorite
import kotlin.math.ceil

@Composable
fun ListScreen(
    viewModel: ListViewModel ,
    select: (RecipesItem) -> Unit ,
    modifier: Modifier = Modifier
) {
    val list = viewModel.list.collectAsState()
    val clickFavorite: (RecipesItem) -> Unit = viewModel::upsertFavorite
    Body(list, select, clickFavorite, modifier)
}

@Composable
fun Body(
    list: State<List<RecipesItem>> ,
    select: (RecipesItem) -> Unit ,
    clickFavorite: (RecipesItem) -> Unit ,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier

            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        StaggeredVerticalGrid(
            maxColumnWidth = 220.dp,
            modifier = Modifier.padding(4.dp)
        ) {
            list.value.forEach { recipe ->
                HomePoster(recipe = recipe, select = select, clickFavorite=clickFavorite)
            }
        }
    }
}

@Composable
    internal fun HomePoster(
    recipe: RecipesItem,
    select: (RecipesItem) -> Unit ,
    clickFavorite: (RecipesItem) -> Unit ,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(4.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.clickable {
                select.invoke(recipe)
            }
        )

        {
            val (image, favorite, minutes, title, sourceName) = createRefs()
            CoilImage(
                    imageModel = recipe.image?:"" ,
                    modifier = Modifier
                        .aspectRatio(1f / recipe.ratio)
                        .constrainAs(image) {
                            centerHorizontallyTo(parent)
                            top.linkTo(parent.top)
                        },
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    // shows a progress indicator when loading an image.
                    loading = {
                        ConstraintLayout(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val indicator = createRef()
                            CircularProgressIndicator(
                                modifier = Modifier.constrainAs(indicator) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                            )
                        }
                    },
                    // shows an error text message when request failed.
                    failure = {
                        Text(
                            text = "image request failed.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    })
            IconFavorite(
                recipe.favorite,
                modifier = Modifier.constrainAs(favorite) {
                    top.linkTo(parent.top, margin = 4.dp)
                    start.linkTo(parent.start, margin = 4.dp)
                }
            ) {
                clickFavorite.invoke(recipe)
            }

            Text(
                text = "${recipe.readyInMinutes}MIN",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(minutes) {
                        top.linkTo(image.bottom)
                    }
                    .padding(start= 4.dp,top = 4.dp)
            )

            Text(
                text = recipe.title?:"",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(minutes.bottom)
                    }
                    .padding(start= 4.dp)
            )

            Text(
                text = "by ${recipe.sourceName}",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(sourceName) {
                        top.linkTo(title.bottom)
                    }
                    .padding(start= 4.dp)
            )

        }
    }
}


@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}



private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}
