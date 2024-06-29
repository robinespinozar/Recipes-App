package com.raerossi.recipesapp.ui.features.detail

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.raerossi.recipesapp.MainActivity
import com.raerossi.recipesapp.R
import com.raerossi.recipesapp.domain.model.Ingredient
import com.raerossi.recipesapp.domain.model.Recipe
import com.raerossi.recipesapp.ui.components.ErrorDialog
import com.raerossi.recipesapp.ui.components.LoadingScreen
import com.raerossi.recipesapp.ui.components.smallShadow
import com.raerossi.recipesapp.ui.features.home.HomeUiState
import com.raerossi.recipesapp.ui.features.home.RecipeItem
import com.raerossi.recipesapp.ui.theme.description_light
import com.raerossi.recipesapp.ui.theme.primary50
import dagger.hilt.android.EntryPointAccessors
import java.sql.Time

@Composable
fun DetailScreen(viewModel: DetailViewModel, onBackClick: () -> Unit) {
    val recipe by viewModel.recipe.observeAsState(Recipe())
    val uiState by viewModel.uiState.collectAsState(DetailUiState())

    DetailScreen(
        recipe = recipe,
        uiState = uiState,
        callbacks = DetailCallbacks(
            onBackClick = { onBackClick() },
            onErrorDialogClick = { /*TODO*/ }
        )
    )
}

@Composable
fun DetailScreen(
    recipe: Recipe?,
    uiState: DetailUiState,
    callbacks: DetailCallbacks
) {
    if (uiState.isLoading) {
        LoadingScreen()
    } else {
        if (uiState.showErrorDialog) {
            ErrorDialog(
                message = uiState.messageError,
                onDissmis = { callbacks.onErrorDialogClick() }
            )
        } else {
            DetailContent(
                recipe = recipe,
                onBackClick = { callbacks.onBackClick() }
            )
        }
    }
}

@Composable
fun DetailContent(
    recipe: Recipe?,
    onBackClick: () -> Unit
) {
    Scaffold(topBar = {
        if (recipe != null) {
            TitleTopBar(title = recipe.name) { onBackClick() }
        }
    }) {
        Column(
            Modifier
                .padding(it)
                .background(Color.Transparent)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ImageAndTimeRecipe(
                imageUrl = recipe?.imageUrl ?: "",
                time = recipe?.time ?: "",
                stars = recipe?.stars ?: 0.0
            )
            Spacer(modifier = Modifier.height(16.dp))
            DetailInformation(
                recipe = recipe,
                modifier = Modifier.alpha(0.8f)
            )

        }
    }
}

@Composable
fun ImageAndTimeRecipe(imageUrl: String, time: String, stars:Double) {
    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(Color(0xFFFCFCFF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .height(270.dp)
                .padding()
                .clip(MaterialTheme.shapes.large),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "recipe image"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.clock),
                contentDescription = "clock",
                tint = MaterialTheme.colorScheme.description_light
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = time,
                color = MaterialTheme.colorScheme.description_light,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_favorite_star_filled),
                    contentDescription = "favorite button",
                    tint = Color.Unspecified
                )
            }
            Text(
                modifier = Modifier.padding(),
                text = stars.toString(),
                color = MaterialTheme.colorScheme.description_light,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun DetailInformation(recipe: Recipe?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.Transparent)
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Level: ${recipe!!.level}",
            style = MaterialTheme.typography.titleSmall,
            color = Color(0xFF1A1C1D)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Ingredients:",
            style = MaterialTheme.typography.titleSmall,
            color = Color(0xFF1A1C1D)
        )
        Spacer(modifier = Modifier.height(8.dp))
        ListIngredients(recipe.ingredients)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Preparation",
            style = MaterialTheme.typography.titleSmall,
            color = Color(0xFF1A1C1D)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = recipe.preparation,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF1A1C1D)
        )
    }
}

@Composable
fun ListIngredients(ingredients: List<Ingredient>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        items(ingredients) {
            IngredientItem(it)
        }
    }
}

@Composable
fun IngredientItem(ingredient: Ingredient) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text =  "- ${ingredient.name} ${ingredient.quantity}",
        style = MaterialTheme.typography.bodyMedium,
        color = Color(0xFF1A1C1D)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTopBar(modifier: Modifier = Modifier, title: String, onBackClick: () -> Unit) {
    TopAppBar(
        modifier = modifier
            .smallShadow()
            .height(58.dp),
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFFFCFCFC),
            navigationIconContentColor = MaterialTheme.colorScheme.primary50
        ),
        title = {
            Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF1A1C1D)
                )
            }
        },
        navigationIcon = {
            Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                IconButton(
                    onClick = { onBackClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Back Arrow",
                        tint = MaterialTheme.colorScheme.primary50
                    )
                }
            }
        }
    )
}


@Composable
fun detailViewModel(id: Int): DetailViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).detailViewModelFactory()

    return viewModel(factory = DetailViewModel.provideFactory(factory, id))
}
