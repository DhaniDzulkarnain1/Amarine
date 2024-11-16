package com.app.amarine.ui.screen.member

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.amarine.R
import com.app.amarine.model.Member
import com.app.amarine.model.member
import com.app.amarine.ui.components.ItemCardMember
import com.app.amarine.ui.components.MySearchBar
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen

@Composable
fun MemberScreen(
    navController: NavController
) {
    var query by remember {
        mutableStateOf("")
    }

    MemberContent(
        member = member,
        searchQuery = query,
        onQueryChange = { query = it },
        onDetailMember = {
            navController.currentBackStackEntry?.savedStateHandle?.set("member", it)
            navController.navigate(Screen.DetailAnggota.route)
        }
    )
}

@Composable
fun MemberContent(
    member: List<Member>,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onDetailMember: (Member) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Anggota",
            )
        }
    ) { contentPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(contentPadding)
                .padding(bottom = 16.dp)
        ) {
            item {
                Column {
                    AsyncImage(
                        model = R.drawable.bg_image_member,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 24.dp,
                                    bottomEnd = 24.dp
                                )
                            )
                    )
                    Text(
                        text = "Daftar Anggota",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            item {
                MySearchBar(
                    value = searchQuery,
                    onValueChange = onQueryChange,
                    placeholder = { Text(text = "Ketik Disini...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Groups,
                        contentDescription = null
                    )
                    Text(
                        text = "${member.size} Anggota"
                    )
                }

            }

            items(member, key = { it.id }) { member ->
                ItemCardMember(
                    member = member,
                    onDetailMember = { onDetailMember(member) },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }

        }
    }
}