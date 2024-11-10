package com.app.amarine.ui.screen.splash

import android.content.Intent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.amarine.R
import com.app.amarine.ui.theme.Primary
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun SplashScreen(onTimeOut: () -> Unit) {
    val currentOnTimeOut by rememberUpdatedState(onTimeOut)


    val marineYPosition = remember { Animatable(50f) }
    val marineXPosition = remember { Animatable(0f) }
    val logomAlpha = remember { Animatable(1f) }
    val textAlpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = currentOnTimeOut) {
        // Animasi marine keluar
        val marineAnimation = async {
            marineYPosition.animateTo(
                targetValue = -200f, // Gerakan marine ke atas
                animationSpec = tween(durationMillis = 2000)
            )
        }

        val logomAnimation = async {
            logomAlpha.animateTo(
                targetValue = 0f, // Menghilangkan logom
                animationSpec = tween(durationMillis = 2000)
            )
        }


        awaitAll(marineAnimation, logomAnimation)


        delay(800)


        marineYPosition.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 2000)
        )


        delay(1000)


        marineXPosition.animateTo(
            targetValue = -100f,
            animationSpec = tween(durationMillis = 1000)
        )


        textAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )


        delay(2.5.seconds)
        onTimeOut()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logom),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .offset(y = 80.dp)
                .alpha(alpha = logomAlpha.value)
        )


        Image(
            painter = painterResource(id = R.drawable.marine),
            contentDescription = null,
            modifier = Modifier
                .size(130.dp)
                .offset(y = marineYPosition.value.dp, x = marineXPosition.value.dp)
        )


        BasicText(
            text = "AMARINE",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFFF15A29),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .offset(x = (marineXPosition.value + 165f).dp, y = 10.dp)
                .alpha(textAlpha.value)
        )
    }
}