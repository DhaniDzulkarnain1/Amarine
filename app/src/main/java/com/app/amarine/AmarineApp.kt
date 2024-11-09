package com.app.amarine

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.get
import com.app.amarine.ui.navigation.BottomNavigationItem
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.theme.Primary

@Composable
fun AmarineApp(
    navController: NavHostController = rememberNavController(),
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val shouldShowBottomBar = when (currentDestination?.route) {
        Screen.Home.route, Screen.Catatan.route,
        Screen.Stock.route, Screen.Anggota.route, -> true
        else -> false
    }

    Scaffold(
        bottomBar = {
            if (!shouldShowBottomBar) {
                return@Scaffold
            }
            BottomBar(navController = navController, currentDestination = currentDestination)
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            icon = ImageVector.vectorResource(id = R.drawable.ic_home),
            screen = Screen.Home,
        ),
        BottomNavigationItem(
            title = "Catatan",
            icon = ImageVector.vectorResource(id = R.drawable.ic_catatan),
            screen = Screen.Catatan,
        ),
        BottomNavigationItem(
            title = "Stock",
            icon = ImageVector.vectorResource(id = R.drawable.ic_stock),
            screen = Screen.Stock,
        ),
        BottomNavigationItem(
            title = "Anggota",
            icon = ImageVector.vectorResource(id = R.drawable.ic_anggota),
            screen = Screen.Anggota,
        ),
    )
    NavigationBar(
        modifier = modifier,
        containerColor = Primary,
    ) {
        items.map { item ->
            NavigationBarItem(
                selected = currentDestination?.route == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph[Screen.Home.route].id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White,
                    unselectedIconColor = Color.White,
                    selectedIconColor = Color.Black
                )
            )
        }
    }
}