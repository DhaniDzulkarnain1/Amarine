package com.app.amarine.ui.screen.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.amarine.R
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.theme.AmarineTheme
import com.app.amarine.ui.theme.Primary
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    navController: NavController,
) {
    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()
    val pagerData = listOf(
        OnBoardingData(
            image = R.drawable.image_onboarding_1,
            title = "Selamat Datang di\nAmarine",
            description = "Catat, pantau dan kelola hasil tangkapan Anda kapan saja, dimana saja",
        ),
        OnBoardingData(
            image = R.drawable.image_onboarding_2,
            title = "Temukan Panduan Terbaik",
            description = "Panduan informatif untuk membantu meningkatkan produktivitas Anda",
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {
            when (it) {
                0 -> OnBoardingPage(
                    onBoardingData = pagerData[0],
                    onNextClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(it + 1)
                        }
                    }
                )

                1 -> OnBoardingPage(
                    onBoardingData = pagerData[1],
                    onNextClick = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.OnBoarding.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        DotsIndicator(
            selectedIndex = pagerState.currentPage,
            dotsCount = pagerData.size,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
        )
    }
}

data class OnBoardingData(
    @DrawableRes val image: Int,
    val title: String,
    val description: String,
)

@Composable
fun OnBoardingPage(
    onBoardingData: OnBoardingData,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = onBoardingData.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(448.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = onBoardingData.title,
            style = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 56.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = onBoardingData.description,
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 56.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        NextButton(onClick = onNextClick)
    }
}

@Composable
fun NextButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = Color.White),
        modifier = Modifier
            .width(160.dp)
            .height(48.dp)
    ) {
        Text(text = "Next")
    }
}

@Composable
fun DotsIndicator(
    selectedIndex: Int,
    dotsCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        (0 until dotsCount).forEachIndexed { index, i ->
            val color = if (selectedIndex == index) Color.Black else Color.LightGray
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .padding(2.dp)
                    .background(color, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnBoardingScreenPreview() {
    AmarineTheme {
        OnBoardingScreen(navController = rememberNavController())
    }
}
