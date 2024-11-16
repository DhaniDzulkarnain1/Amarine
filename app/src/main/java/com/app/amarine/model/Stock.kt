package com.app.amarine.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stock(
    val id: Int,
    val type: String,
    val detail: List<DetailStock>,
    val available: Int = if (detail.isNotEmpty()) detail.last().available else 0 // Fallback untuk daftar kosong
) : Parcelable

@Parcelize
data class DetailStock(
    val date: String,
    val enter: Int,
    val sold: Int,
    val available: Int
) : Parcelable

val stocks = listOf(
    Stock(
        id = 0,
        type = "Ikan Nila",
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
            ),
        )
    ),
    Stock(
        id = 1,
        type = "Ikan Kerapu",
        detail = listOf(
            DetailStock(
                date = "8-11-2024",
                enter = 25,
                sold = 10,
                available = 15
            ),
            DetailStock(
                date = "9-11-2024",
                enter = 10,
                sold = 10,
                available = 15
            ),
            DetailStock(
                date = "10-11-2024",
                enter = 10,
                sold = 8,
                available = 17
            ),
        )
    ),
    Stock(
        id = 2,
        type = "Ikan Bawal",
        detail = listOf(
            DetailStock(
                date = "9-11-2024",
                enter = 30,
                sold = 4,
                available = 26
            ),
            DetailStock(
                date = "12-11-2024",
                enter = 5,
                sold = 10,
                available = 21
            ),
        )
    ),
    Stock(
        id = 3,
        type = "Ikan Tongkol",
        detail = listOf(
            DetailStock(
                date = "3-11-2024",
                enter = 45,
                sold = 15,
                available = 30
            ),
            DetailStock(
                date = "5-11-2024",
                enter = 15,
                sold = 30,
                available = 15
            ),
            DetailStock(
                date = "6-11-2024",
                enter = 10,
                sold = 25,
                available = 0
            ),
            DetailStock(
                date = "7-11-2024",
                enter = 30,
                sold = 0,
                available = 30
            ),
            DetailStock(
                date = "9-11-2024",
                enter = 30,
                sold = 15,
                available = 15
            ),
        )
    ),
    Stock(
        id = 4,
        type = "Sotong",
        detail = listOf(
            DetailStock(
                date = "3-11-2024",
                enter = 10,
                sold = 7,
                available = 3
            ),
            DetailStock(
                date = "5-11-2024",
                enter = 10,
                sold = 2,
                available = 11
            ),
        )
    ),
)
