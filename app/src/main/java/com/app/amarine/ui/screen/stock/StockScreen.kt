package com.app.amarine.ui.screen.stock

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.amarine.model.Stock
import com.app.amarine.model.stocks
import com.app.amarine.ui.components.MyTopAppBar
import com.app.amarine.ui.navigation.Screen
import com.app.amarine.ui.theme.Primary
import com.app.amarine.ui.theme.Primary200

@Composable
fun StockScreen(navController: NavController) {
    StockContent(
        stock = stocks,
        onDetailClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set("stock", it)
            navController.navigate(Screen.DetailStock.route)
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
                text = "Kelola Stok",
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
                        text = "No",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(0.5f)
                    )
                    Text(
                        text = "Jenis",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Text(
                        text = "Kuantitas (Kg)",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Text(
                        text = "Aksi",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1.5f)
                    )
                }

                stock.forEachIndexed { index, stock ->
                    Column {

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(8.dp)
                        ) {
                            Text(
                                text = (stock.id + 1).toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .weight(0.5f)
                            )
                            Text(
                                text = stock.type,
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
                            Button(
                                onClick = { onDetailClick(stock) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Primary,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text("Detail")
                            }
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