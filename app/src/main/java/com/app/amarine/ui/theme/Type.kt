package com.app.amarine.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.app.amarine.R

// Set of Material typography styles to start with
val poppinsFamily = FontFamily(
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_extra_light, FontWeight.ExtraLight),
)
val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.SansSerif,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
    headlineMedium = Typography().headlineMedium.copy(
        fontFamily = poppinsFamily
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontFamily = poppinsFamily
    ),
    bodySmall = Typography().bodySmall.copy(
        fontFamily = poppinsFamily
    ),
    displaySmall = Typography().displaySmall.copy(
        fontFamily = poppinsFamily
    ),
    headlineSmall = Typography().headlineSmall.copy(
        fontFamily = poppinsFamily
    ),
    titleMedium = Typography().titleMedium.copy(
        fontFamily = poppinsFamily
    ),
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = poppinsFamily
    ),
    titleLarge = Typography().titleLarge.copy(
        fontFamily = poppinsFamily
    ),
    labelSmall = Typography().labelSmall.copy(
        fontFamily = poppinsFamily
    ),
    labelMedium = Typography().labelMedium.copy(
        fontFamily = poppinsFamily
    ),
    titleSmall = Typography().titleSmall.copy(
        fontFamily = poppinsFamily
    ),
    labelLarge = Typography().labelLarge.copy(
        fontFamily = poppinsFamily
    )
)