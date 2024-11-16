package com.app.amarine

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Catatan.route) {
            CatatanScreen()
        }
        composable(Screen.Stock.route) {
            StockScreen()
        }
        composable(Screen.Anggota.route) {
            AnggotaScreen()
        }
        composable("detail_catatan") {
            DetailCatatanScreen()
        }
        composable("detail_stock") {
            DetailStockScreen()
        }
        composable("detail_anggota") {
            DetailAnggotaScreen()
        }
    }
}

@Composable
fun AmarineApp(
    navController: NavHostController = rememberNavController(),
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val shouldShowBottomBar = when (currentDestination?.route) {
        Screen.Home.route, Screen.Catatan.route, Screen.Stock.route, Screen.Anggota.route -> true
        else -> false
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                AnimatedBottomBar(navController)
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AnimatedBottomBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem("Beranda", R.drawable.ic_home, Screen.Home),
        BottomNavItem("Catatan", R.drawable.ic_catatan, Screen.Catatan),
        BottomNavItem("Stok", R.drawable.ic_stock, Screen.Stock),
        BottomNavItem("Anggota", R.drawable.ic_anggota, Screen.Anggota)
    )

    val selectedIndex by remember(currentRoute) {
        derivedStateOf {
            when (currentRoute) {
                Screen.Home.route -> 0
                Screen.Catatan.route, "detail_catatan" -> 1
                Screen.Stock.route, "detail_stock" -> 2
                Screen.Anggota.route, "detail_anggota" -> 3
                else -> 0
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedIndex == index

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() }, // Menghilangkan ripple
                            indication = null
                        ) {
                            navController.navigate(item.screen.route) {
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = item.iconResId),
                            contentDescription = item.label,
                            tint = if (isSelected) Color(0xFFF15A29) else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.label,
                            color = if (isSelected) Color(0xFFF15A29) else Color.Gray,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Home Screen")
    }
}

@Composable
fun CatatanScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Catatan Screen")
    }
}

@Composable
fun StockScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Stock Screen")
    }
}

@Composable
fun AnggotaScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Anggota Screen")
    }
}

@Composable
fun DetailCatatanScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Detail Catatan Screen")
    }
}

@Composable
fun DetailStockScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Detail Stock Screen")
    }
}

@Composable
fun DetailAnggotaScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Detail Anggota Screen")
    }
}

data class BottomNavItem(
    val label: String,
    val iconResId: Int,
    val screen: Screen,
)

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Catatan : Screen("catatan")
    object Stock : Screen("stock")
    object Anggota : Screen("anggota")
}
