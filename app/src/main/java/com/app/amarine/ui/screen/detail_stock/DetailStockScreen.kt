package com.app.amarine.ui.screen.detail_stock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.amarine.model.Stock
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.screen.detail_member.DetailMemberContent
import com.app.amarine.ui.theme.AmarineTheme
import com.app.amarine.ui.theme.Primary200

@Composable
fun DetailStockScreen(
    stock: Stock?,
    navController: NavController
) {
    DetailStockContent(
        stock = stock,
        onNavigateUp = { navController.navigateUp() }
    )
}

@Composable
fun DetailStockContent(
    stock: Stock?,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Detail Stock",
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

        val scrollState = rememberScrollState()

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {

            Text(
                text = "${stock?.type}",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Primary200, MaterialTheme.shapes.large)
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
            ) {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Tanggal",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Text(
                        text = "Masuk (Kg)",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Text(
                        text = "Terjual (Kg)",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Text(
                        text = "Total Stok",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                    )
                }

                stock?.detail?.forEachIndexed { index, stock ->
                    Column {

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(8.dp)
                        ) {
                            Text(
                                text = (stock.date),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .weight(1f)
                            )
                            Text(
                                text = stock.enter.toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .weight(1f)
                            )
                            Text(
                                text = stock.sold.toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .weight(1f)
                            )
                            Text(
                                text = stock.available.toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .weight(1f)
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .size(1.dp)
                                .background(Color.Black)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailStockContentPreview() {
    AmarineTheme {
        DetailStockContent(stock = null, onNavigateUp = { /*TODO*/ })
    }
}