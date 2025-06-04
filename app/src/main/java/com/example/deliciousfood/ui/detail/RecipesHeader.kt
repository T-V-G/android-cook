

package com.example.deliciousfood.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.deliciousfood.R
import com.skydoves.landscapist.coil.CoilImage
import com.example.deliciousfood.ui.widget.verticalGradient
import com.example.deliciousfood.domain.model.RecipesItem
import androidx.compose.material.icons.automirrored.filled.ArrowBack
//@ExperimentalCoilApi
@Composable
fun RecipesHeader(
    recipe: RecipesItem ,
    navController: NavController
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        val (image, info, topBar, title) = createRefs()
        CoilImage(
            imageModel = recipe.image?:"" ,
            modifier = Modifier
                .fillMaxWidth()
                .height(346.dp)
                .constrainAs(image) {
                    linkTo(
                        start = parent.start ,
                        top = parent.top ,
                        end = parent.end ,
                        bottom = info.top ,
                        bottomMargin = (-32).dp
                    )
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },

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
                        },
                        color = Color.White
                    )
                }
            },
            // shows an error text message when request failed.
            failure = {
                Image(
                    painter = painterResource(R.drawable.placeholder) ,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "")
            })

        DetailsAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .constrainAs(topBar) {
                    linkTo(start = parent.start , end = parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                }
        ) { navController.navigateUp() }

        Text(
            text = recipe.title ?: "",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Start,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp ,
                    end = 100.dp ,
                    bottom = 8.dp
                )
                .constrainAs(title) {
                    linkTo(
                        start = parent.start ,
                        end = parent.end
                    )
                    bottom.linkTo(info.top)
                }
        )

        Surface(
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            modifier = Modifier
                .constrainAs(info) {
                    linkTo(start = parent.start, end = parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(35.dp)
                }
        ) {
            Spacer(
                modifier = Modifier
                    .height(40.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
        }
    }
}

@Composable
fun DetailsAppBar(modifier: Modifier, onBackPressed: () -> Unit) {
    ConstraintLayout(modifier) {
        val (back, share) = createRefs()
        RecipeGradient(modifier = Modifier.fillMaxSize())
        IconButton(
            onClick = onBackPressed,
            Modifier.constrainAs(back) {
                start.linkTo(parent.start, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

    }
}

@Composable
fun RecipeGradient(modifier: Modifier) {
    Spacer(
        modifier = modifier.verticalGradient()
    )
}
