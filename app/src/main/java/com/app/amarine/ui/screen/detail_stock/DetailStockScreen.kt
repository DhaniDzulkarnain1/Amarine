package com.app.amarine.ui.screen.detail_stock

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.app.amarine.model.DetailStock
import com.app.amarine.model.Stock
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.theme.Primary200

@Composable
fun DetailStockScreen(
    stock: Stock?,
    navController: NavController
) {
    if (stock == null) {
        // Log jika stok tidak ditemukan
        Log.d("DetailStockScreen", "Stock is null. Data not found!")
        // Tampilkan pesan error jika stok tidak ditemukan
        Text(
            text = "Data stok tidak ditemukan",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    // Log jika stok ditemukan
    Log.d("DetailStockScreen", "Displaying stock: $stock")

    DetailStockContent(
        stock = stock,
        onNavigateUp = { navController.navigateUp() }
    )
}

@Composable
fun DetailStockContent(
    stock: Stock,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Detail Stok",
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "Kembali",
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
            // Menampilkan jenis stok
            Text(
                text = stock.type,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Header tabel
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Primary200)
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Tanggal",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1.2f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Masuk",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Terjual",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Total Stok",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }

                    // Konten tabel
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        stock.detail.forEachIndexed { index, detail ->
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(if (index % 2 == 0) Color.White else Color(0xFFF8F8F8))
                                    .padding(vertical = 12.dp, horizontal = 16.dp)
                            ) {
                                Text(
                                    text = detail.date,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1.2f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "${detail.enter} Kg",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "${detail.sold} Kg",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "${detail.available} Kg",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailStockContentPreview() {
    MaterialTheme {
        DetailStockContent(
            stock = Stock(
                id = 0,
                type = "Ikan Nila",
                available = 15,
                detail = listOf(
                    DetailStock(
                        date = "7-11-2024",
                        enter = 30,
                        sold = 15,
                        available = 15
                    ),
                    DetailStock(
                        date = "9-11-2024",
                        enter = 20,
                        sold = 10,
                        available = 25
                    ),
                    DetailStock(
                        date = "10-11-2024",
                        enter = 0,
                        sold = 12,
                        available = 13
                    )
                )
            ),
            onNavigateUp = {}
        )
    }
}
