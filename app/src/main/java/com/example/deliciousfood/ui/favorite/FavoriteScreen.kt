package com.example.deliciousfood.ui.favorite

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.skydoves.landscapist.coil.CoilImage
import com.example.deliciousfood.R
import com.example.deliciousfood.domain.model.RecipesItem
import com.example.deliciousfood.ui.common.IconFavorite
import com.example.deliciousfood.ui.home.cart.SwipeDismissItem


@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel ,
    select: (RecipesItem) -> Unit ,
    modifier: Modifier = Modifier
) {
    val list = viewModel.list.collectAsState()
    if (list.value.isEmpty()) {
        EmptyScreen()
    } else {
        ListScreen(modifier , list , select , viewModel )
    }
}

@Composable
private fun ListScreen(
    modifier: Modifier ,
    list: State<List<RecipesItem>> ,
    select: (RecipesItem) -> Unit ,
    viewModel: FavoriteViewModel ,
) {
    LazyColumn(
        modifier = modifier ,
        contentPadding = PaddingValues(start = 8.dp , top = 10.dp , end = 8.dp , bottom = 10.dp) ,
        verticalArrangement = Arrangement.spacedBy(8.dp) ,
    ) {
        items(list.value) {
            ItemContent(
                item = it ,
                click = select ,
                clickFavorite = viewModel::upsertFavorite
            )
        }
    }
}

@Composable
private fun EmptyScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center ,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Icon(
            painterResource(id = R.drawable.ic_flask_outline) ,
            contentDescription = null ,
            tint = MaterialTheme.colorScheme.primary ,
            modifier = Modifier.size(50.dp)
        )
        Text(
            stringResource(R.string.empty) ,
            style = MaterialTheme.typography.bodyLarge ,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun ItemContent(
    item: RecipesItem ,
    click: (RecipesItem) -> Unit ,
    clickFavorite: (RecipesItem) -> Unit
) {
    SwipeDismissItem(
        background = { offsetX ->
            /*Background color changes from light gray to red when the
            swipe to delete with exceeds 160.dp*/
            val backgroundColor = if (offsetX < -160.dp) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onError
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(backgroundColor) ,
                horizontalAlignment = Alignment.End ,
                verticalArrangement = Arrangement.Center
            ) {
                // Set 4.dp padding only if offset is bigger than 160.dp
                val padding: Dp by animateDpAsState(
                    if (offsetX > -160.dp) 4.dp else 0.dp
                )
                Box(
                    Modifier
                        .width(offsetX * -1)
                        .padding(padding)
                ) {
                    // Height equals to width removing padding
                    val height = (offsetX + 8.dp) * -1
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height)
                            .align(Alignment.Center) ,
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.error
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize() ,
                            contentAlignment = Alignment.Center
                        ) {
                            // Icon must be visible while in this width range
                            if (offsetX < -40.dp && offsetX > -152.dp) {
                                // Icon alpha decreases as it is about to disappear
                                val iconAlpha: Float by animateFloatAsState(
                                    if (offsetX < -120.dp) 0.5f else 1f
                                )

                                Icon(
                                    imageVector = Icons.Filled.DeleteForever ,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .graphicsLayer(alpha = iconAlpha) ,
                                    tint = MaterialTheme.colorScheme.onError ,
                                    contentDescription = null ,
                                )
                            }
                            /*Text opacity increases as the text is supposed to appear in
                            the screen*/
                            val textAlpha by animateFloatAsState(
                                if (offsetX > -144.dp) 0.5f else 1f
                            )
                            if (offsetX < -120.dp) {
                                Text(
                                    text = stringResource(id = R.string.remove_item) ,
                                    style = MaterialTheme.typography.titleMedium ,
                                    color = MaterialTheme.colorScheme.onError ,
                                    textAlign = TextAlign.Center ,
                                    modifier = Modifier
                                        .graphicsLayer(
                                            alpha = textAlpha
                                        )
                                )
                            }
                            if (offsetX < -350.dp) {
                                clickFavorite.invoke(item)
                            }
                        }
                    }
                }
            }
        } ,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)

                .clickable { click.invoke(item) } ,
            color = MaterialTheme.colorScheme.primaryContainer ,
            tonalElevation = 8.dp ,
            shape = RoundedCornerShape(10.dp)
        ) {

            ConstraintLayout(
                modifier = Modifier
            ) {
                val (image , favorite , minutes , title , sourceName) = createRefs()

                CoilImage(
                    imageModel = item.image ?: "" ,
                    alignment = Alignment.TopCenter ,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .constrainAs(image) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .clip(MaterialTheme.shapes.medium) ,
                    contentDescription = null ,
                    contentScale = ContentScale.Crop ,
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
                    } ,
                    // shows an error text message when request failed.
                    failure = {
                        Text(
                            text = "image request failed." ,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    })

                Text(
                    text = "${item.readyInMinutes}MIN" ,
                    style = MaterialTheme.typography.bodyLarge ,
                    modifier = Modifier.constrainAs(minutes) {
                        start.linkTo(image.end , 8.dp)
                        top.linkTo(image.top , 4.dp)
                    }
                )
                Text(
                    text = item.title ?: "" ,
                    style = MaterialTheme.typography.displayMedium ,
                    textAlign = TextAlign.Center ,
                    maxLines = 1 ,
                    overflow = TextOverflow.Ellipsis ,
                    modifier = Modifier.constrainAs(title) {
                        start.linkTo(minutes.start)
                        top.linkTo(minutes.bottom , 4.dp)
                    }
                )

                Text(
                    text = "by ${item.sourceName}" ,
                    style = MaterialTheme.typography.displayLarge ,
                    modifier = Modifier.constrainAs(sourceName) {
                        start.linkTo(title.start)
                        top.linkTo(title.bottom , 4.dp)
                    }
                )

                IconFavorite(
                    item.favorite ,
                    modifier = Modifier.constrainAs(favorite) {
                        end.linkTo(parent.end , 8.dp)
                        bottom.linkTo(parent.bottom , 8.dp)
                    }
                ) { clickFavorite.invoke(item) }
            }
        }
    }
}

