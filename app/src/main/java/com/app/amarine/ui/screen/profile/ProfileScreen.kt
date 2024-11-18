package com.app.amarine.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.amarine.ui.components.ContainerBox
import com.app.amarine.ui.components.ItemProfileMenu
import com.app.amarine.ui.components.ItemProfileUser
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.components.RowType
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.theme.Background

@Composable
fun ProfileScreen(navController: NavController) {
    ProfileContent(
        imageUrl = null,
        name = null,
        onEditProfileClick = { navController.navigate(Screen.EditProfile.route) },
        onChangePasswordClick = { navController.navigate(Screen.ChangePassword.route) },
        onFavoriteArticles = { navController.navigate(Screen.FavoriteArticles.route) },
        onSettingClick = { navController.navigate(Screen.SettingProfile.route) },
        onLogoutClick = { navController.navigate(Screen.Login.route) },
        onNavigateUp = { navController.navigateUp() }
    )
}

@Composable
fun ProfileContent(
    imageUrl: String?,
    name: String?,
    onEditProfileClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onFavoriteArticles: () -> Unit,
    onSettingClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Profil",
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ContainerBox(modifier = Modifier.padding(horizontal = 16.dp)) {
                ItemProfileUser(
                    name = name,
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ContainerBox(modifier = Modifier.padding(horizontal = 16.dp)) {
                ItemProfileMenu(
                    title = {
                        Text(
                            text = "Edit Profil",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                    },
                    iconStart = {
                        Icon(
                            imageVector = Icons.Outlined.ModeEdit,
                            contentDescription = null,
                        )
                    },
                    rowType = RowType.TOP,
                    iconEnd = {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null,
                        )
                    },
                    onClick = onEditProfileClick
                )
                HorizontalDivider(
                    color = Background
                )
                ItemProfileMenu(
                    title = {
                        Text(
                            text = "Ubah Kata Sandi",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                    },
                    iconStart = {
                        Icon(
                            imageVector = Icons.Outlined.Security,
                            contentDescription = null,
                        )
                    },
                    iconEnd = {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null,
                        )
                    },
                    onClick = onChangePasswordClick
                )
                HorizontalDivider(
                    color = Background
                )
                ItemProfileMenu(
                    title = {
                        Text(
                            text = "Artikel Favorit",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                    },
                    iconStart = {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                        )
                    },
                    iconEnd = {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null,
                        )
                    },
                    onClick = onFavoriteArticles
                )
                HorizontalDivider(
                    color = Background
                )
                ItemProfileMenu(
                    title = {
                        Text(
                            text = "Pengaturan",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                    },
                    iconStart = {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = null,
                        )
                    },
                    iconEnd = {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null,
                        )
                    },
                    onClick = onSettingClick
                )
                HorizontalDivider(
                    color = Background
                )
                ItemProfileMenu(
                    title = {
                        Text(
                            text = "Keluar",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                    },
                    iconStart = {
                        Icon(
                            imageVector = Icons.Outlined.Logout,
                            contentDescription = null,
                        )
                    },
                    rowType = RowType.BOTTOM,
                    iconEnd = {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null,
                        )
                    },
                    onClick = onLogoutClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileContent(
        imageUrl = null,
        name = null,
        onEditProfileClick = { /*TODO*/ },
        onChangePasswordClick = { /*TODO*/ },
        onFavoriteArticles = { /*TODO*/ },
        onSettingClick = { /*TODO*/ },
        onLogoutClick = { /*TODO*/ },
        onNavigateUp = {}
    )
}