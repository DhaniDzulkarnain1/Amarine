package com.app.amarine.ui.screen.detail_member

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.model.Member
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.screen.add_note.AddNoteContent
import com.app.amarine.ui.theme.AmarineTheme
import com.app.amarine.ui.theme.Primary200

@Composable
fun DetailMemberScreen(
    member: Member?,
    navController: NavController
) {
   DetailMemberContent(
       member = member,
       onNavigateUp = { navController.navigateUp() }
   )
}

@Composable
fun DetailMemberContent(
    member: Member?,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Detail Anggota",
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null,
                        )
                    }
                },
            )
        }
    ) { contentPadding ->

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .background(Primary200, MaterialTheme.shapes.large)
                        .fillMaxWidth()
                ) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = member?.imageResourceId,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(240.dp)
                                .clip(CircleShape)
                        )

                        Text(
                            text = member?.name.toString(),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Text(
                            text = "Nomor Telepon",
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedTextField(
                            value = member?.phoneNumber.toString(),
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .background(color = Color.White, shape = MaterialTheme.shapes.medium)
                        )

                        Text(
                            text = "Email",
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedTextField(
                            value = member?.email.toString(),
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .background(color = Color.White, shape = MaterialTheme.shapes.medium)
                        )

                        Text(
                            text = "Tanggal Lahir",
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedTextField(
                            value = member?.birthDate.toString(),
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .background(color = Color.White, shape = MaterialTheme.shapes.medium)
                        )

                        Text(
                            text = "Status",
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedTextField(
                            value = member?.status.toString(),
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .background(color = Color.White, shape = MaterialTheme.shapes.medium)
                        )

                        Text(
                            text = "Alamat",
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedTextField(
                            value = member?.address.toString(),
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .background(color = Color.White, shape = MaterialTheme.shapes.medium)
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun DetailMemberContentPreview() {
    AmarineTheme {
       DetailMemberContent(member = null, onNavigateUp = { /*TODO*/ })
    }
}