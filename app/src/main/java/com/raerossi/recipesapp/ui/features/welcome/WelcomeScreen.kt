package com.raerossi.recipesapp.ui.features.welcome

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raerossi.recipesapp.R
import com.raerossi.recipesapp.ui.components.GradientButton
import com.raerossi.recipesapp.ui.components.SetSystemColors
import com.raerossi.recipesapp.ui.theme.description_light
import com.raerossi.recipesapp.ui.theme.primaryGradient
import com.raerossi.recipesapp.ui.theme.title

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    SetSystemColors(colorStatusBar = Color(0xFFFFFFFE))

    Box(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color(0xFFFFFFFE))
    ) {
        WelcomeContent(modifier = Modifier.align(Alignment.Center))
        StartButton(modifier = Modifier.align(Alignment.BottomCenter)) { onStartClick() }
    }
}

@Composable
fun WelcomeContent(modifier: Modifier = Modifier) {
    Column(modifier) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(0.dp, 500.dp),
            painter = painterResource(id = R.drawable.food_explotion),
            contentDescription = "welcome image",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        TitleAndDescription(
            title = "Welcome to Recipes!",
            description = "Discover delicious recipes, from quick meals to gourmet dishes. Explore, cook, and enjoy your culinary journey with us. Happy cooking!"
        )
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun TitleAndDescription(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.title,
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.description_light,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun StartButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    GradientButton(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 64.dp),
        text = "Start Now",
        textColor = Color.White,
        gradient = MaterialTheme.colorScheme.primaryGradient,
        onClick = { onClick() }
    )
}
