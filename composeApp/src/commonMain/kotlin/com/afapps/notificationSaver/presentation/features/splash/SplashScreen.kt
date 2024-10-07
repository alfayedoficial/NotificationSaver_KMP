package com.afapps.notificationSaver.presentation.features.splash

import NSaver
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.afapps.notificationSaver.core.theme.TemplateBackground
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.ic_nsaver
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.vectorResource

/**
 * Created by [Ali Al Fayed](https://www.linkedin.com/in/alfayedoficial)
 * About class :
 * Date Created: 29/09/2024 - 1:16 AM
 * Last Modified: 29/09/2024 - 1:16 AM
 */

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onMove: () -> Unit = {}) {

    var radius by remember { mutableStateOf(0f) }
    var textVisible by remember { mutableStateOf(false) }

    // The target radius based on the screen size
    val targetRadius = 2000f // Set this to the full size you want (e.g., full screen width or height)

    // Animate the radius
    val animatedRadius by animateFloatAsState(
        targetValue = radius,
        animationSpec = tween(durationMillis = 30) // Duration for each animation step
    )

    // Use LaunchedEffect to update radius
    LaunchedEffect(Unit) {
        while (radius < targetRadius) {
            radius += 50f // Increment the radius
            delay(30) // Delay for 500ms
        }
        delay(500)
        textVisible = true
        delay(1000)
        onMove()
    }


    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = TemplateBackground,
                radius = animatedRadius
            )
        }

        Box(modifier = Modifier.fillMaxSize(0.5f) , contentAlignment = Alignment.Center) {

            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally ,
                verticalArrangement = Arrangement.Center) {

                Image(painter = rememberVectorPainter(image = vectorResource(Res.drawable.ic_nsaver)),
                    contentDescription = "Logo",
                    modifier = Modifier.size(150.dp)
                )

                AnimatedVisibility(
                    visible = textVisible,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(
                        text = "Notification Saver",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White // Adjust the text color as needed
                    )
                }
            }
        }





    }
}

