package com.app.amarine.ui.screen.edit_profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.amarine.UserPreferences
import com.app.amarine.ui.components.ContainerBox
import com.app.amarine.ui.components.ItemProfileUser
import com.app.amarine.ui.components.MyPrimaryButton
import com.app.amarine.ui.components.MyTextField
import com.app.amarine.ui.components.MyTopAppBar

@Composable
fun EditProfileScreen(navController: NavController) {
    var name by remember { mutableStateOf("Guest") }
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var placeDateOfBirth by remember { mutableStateOf("") }

    EditProfileContent(
        imageUrl = "",
        name = name,
        fullName = fullName,
        email = email,
        phoneNumber = phoneNumber,
        alamat = gender,
        placeDateOfBirth = placeDateOfBirth,
        onFullNameChange = { fullName = it },
        onEmailChange = { email = it },
        onPhoneNumberChange = { phoneNumber = it },
        onGenderChange = { gender = it },
        onPlaceDateOfBirthChange = { placeDateOfBirth = it },
        onSaveClick = { navController.navigateUp() },
        onNavigateUp = { navController.navigateUp() }
    )
}

@Composable
fun EditProfileContent(
    imageUrl: String?,
    name: String?,
    fullName: String,
    email: String,
    phoneNumber: String,
    alamat: String,
    placeDateOfBirth: String,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onPlaceDateOfBirthChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Edit Profil",
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
            val userPreferences = UserPreferences.current()
            val userName = userPreferences.getUserNama()

            Spacer(modifier = Modifier.height(16.dp))
            ContainerBox(modifier = Modifier.padding(horizontal = 16.dp)) {
                ItemProfileUser(
                    name = userName,
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            MyTextField(
                value = fullName,
                onValueChange = onFullNameChange,
                placeholder = { Text(text = userName) },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            MyTextField(
                value = phoneNumber,
                onValueChange = onPhoneNumberChange,
                placeholder = { Text(text = "Nomor Telepon") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            val userEmail = userPreferences.getUserEmail()

            MyTextField(
                value = email,
                onValueChange = onEmailChange,
                placeholder = { Text(text = userEmail) },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            MyTextField(
                value = alamat,
                onValueChange = onGenderChange,
                placeholder = { Text(text = "Alamat") },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            MyTextField(
                value = placeDateOfBirth,
                onValueChange = onPlaceDateOfBirthChange,
                placeholder = { Text(text = "Tanggal Lahir") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.CalendarToday,
                        contentDescription = null,
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            MyPrimaryButton(
                text = "Simpan Perubahan",
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }
    }
}