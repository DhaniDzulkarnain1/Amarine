package com.app.amarine.ui.screen.stock

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.amarine.model.Stock
import com.app.amarine.model.stocks
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.theme.Primary

private val CardBackground = Color(0xFFFFF3E0) // Very soft orange background

@Composable
fun StockScreen(navController: NavController) {
    StockContent(
        stock = stocks,
        onDetailClick = { stock ->
            Log.d("StockScreen", "Navigating to DetailStockScreen with stock ID: ${stock.id}")
            navController.navigate(Screen.DetailStock.createRoute(stock.id))
        }
    )
}

@Composable
fun StockContent(
    stock: List<Stock>,
    onDetailClick: (Stock) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Amarine",
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
                text = "Stok",
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
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CardBackground)
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = "No",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(0.5f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Jenis",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Kuantitas (Kg)",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Aksi",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        stock.forEachIndexed { index, item ->
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(if (index % 2 == 0) Color.White else Color(0xFFF8F8F8))
                                    .padding(vertical = 12.dp, horizontal = 16.dp)
                            ) {
                                Text(
                                    text = (item.id + 1).toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(0.5f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = item.type,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "${item.available} Kg",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Button(
                                    onClick = { onDetailClick(item) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Primary,
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Detail")
                                }
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
private fun StockScreenPreview() {
    MaterialTheme {
        StockScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
private fun StockContentPreview() {
    MaterialTheme {
        StockContent(
            stock = stocks,
            onDetailClick = {}
        )
    }
}