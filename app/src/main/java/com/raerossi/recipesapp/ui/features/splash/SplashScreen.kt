package com.raerossi.retotecnico.ui.features.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raerossi.recipesapp.R
import com.raerossi.recipesapp.ui.components.SetSystemColors
import com.raerossi.recipesapp.ui.theme.backgroundGradient
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(toWelcomeScreen: () -> Unit) {
    SetSystemColors(colorStatusBar = Color(0xFF22A45C))
    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.8f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
        delay(1300L)
        toWelcomeScreen()
    }
    SplashScreen(scale = scale.value)
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    scale: Float
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = MaterialTheme.colorScheme.backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .scale(scale)
                .padding(all = 64.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "splash logo"
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(scale = 0f)
}