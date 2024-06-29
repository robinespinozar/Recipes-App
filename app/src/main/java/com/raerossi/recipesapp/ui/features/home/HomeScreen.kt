package com.raerossi.recipesapp.ui.features.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.raerossi.recipesapp.R
import com.raerossi.recipesapp.domain.model.Recipe
import com.raerossi.recipesapp.ui.components.ErrorDialog
import com.raerossi.recipesapp.ui.components.LoadingScreen
import com.raerossi.recipesapp.ui.components.SearchBar
import com.raerossi.recipesapp.ui.components.smallShadow
import com.raerossi.recipesapp.ui.theme.title

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onRecipeClick: (Recipe) -> Unit
) {
    val filterValue by viewModel.filterValue.observeAsState("")
    val recipes by viewModel.recipes.collectAsState(emptyList())
    val uiState by viewModel.uiState.collectAsState(HomeUiState())

    HomeScreen(
        filterValue = filterValue,
        recipes = recipes,
        uiState = uiState,
        callbacks = HomeCallbacks(
            onRecipeClick = { onRecipeClick(it) },
            onErrorDialogClick = { viewModel.hideErrorDialog() },
            onSearchTextChanged = { viewModel.getFilteredRecipes(it) }
        )
    )
}

@Composable
fun HomeScreen(
    filterValue: String,
    recipes: List<Recipe>,
    uiState: HomeUiState,
    callbacks: HomeCallbacks
) {
    Scaffold(
        topBar = { CustomTopBar("Discover Recipes!") },
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            SearchBar(filterValue = filterValue) { callbacks.onSearchTextChanged(it) }
            if (uiState.isLoading) {
                LoadingScreen()
            } else {
                if (uiState.showErrorDialog) {
                    ErrorDialog(
                        message = uiState.messageError,
                        onDissmis = { callbacks.onErrorDialogClick() }
                    )
                } else {
                    HomeContent(
                        recipes = recipes,
                        onRecipeClick = { callbacks.onRecipeClick(it) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(recipes) { recipe ->
            RecipeItem(recipe) { onRecipeClick(it) }
        }
    }
}


@Composable
fun RecipeItem(recipe: Recipe, onClick: (Recipe) -> Unit) {
    Card(
        Modifier
            .height(285.dp)
            .clickable { onClick(recipe) },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, Color(0xFFEDF3EF))
    ) {
        CardContent(recipe = recipe)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(title: String) {
    TopAppBar(
        modifier = Modifier.smallShadow(),
        colors = topAppBarColors(
            containerColor = Color(0xFFFCFCFC),
            titleContentColor = MaterialTheme.colorScheme.title,
        ),
        title = {
            Text(
                modifier = Modifier.padding(),
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        }
    )
}

@Composable
fun CardContent(
    modifier: Modifier = Modifier,
    recipe: Recipe
) {
    val painter = rememberAsyncImagePainter(recipe.imageUrl)
    val transition by animateFloatAsState(
        targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
        label = "recipe transition"
    )
    Box(
        modifier = modifier
            .background(Color(0XFFF0F0EA))
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(transition)
        )
        Qualification(
            modifier = Modifier
                .padding(end = 4.dp)
                .align(Alignment.TopEnd),
            voteAverage = recipe.stars.toString()
        )
        Description(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 12.dp)
                .align(Alignment.BottomStart),
            name = recipe.name,
            time = recipe.time
        )
    }
}

@Composable
fun Description(
    modifier: Modifier = Modifier,
    name: String,
    time: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = name,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.clock),
                contentDescription = "clock",
                tint = Color(0xFFEEEEEE)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = time,
                color = Color(0xFFEEEEEE),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun Qualification(modifier: Modifier = Modifier, voteAverage: String) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(),
            text = voteAverage,
            color = Color(0xFFEEEEEE),
            style = MaterialTheme.typography.bodyLarge
        )
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite_star_filled),
                contentDescription = "favorite button",
                tint = Color.Unspecified
            )
        }
    }
}
