package com.app.amarine.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Parcelize
data class Stock(
    val id: Int,
    val nama: String,
    val jenis: String,
    val kuantitas: Int = 0,
    val type: String = nama,
    val detail: List<DetailStock> = listOf(
        DetailStock(
            date = getCurrentDate(),
            enter = kuantitas,
            sold = 0,
            available = kuantitas
        )
    ),
    val available: Int = kuantitas,
    val tanggal: String? = null,
    val waktu: String? = null,
    val lokasi_penyimpanan: String? = null
) : Parcelable

@Parcelize
data class DetailStock(
    val date: String,
    val enter: Int = 0,
    val sold: Int = 0,
    val available: Int = 0
) : Parcelable

@Parcelize
data class StockResponse(
    val status: Boolean,
    val message: String,
    val data: List<Stock>
) : Parcelable

sealed class StockState {
    object Loading : StockState()
    data class Success(val data: List<Stock>) : StockState()
    data class Error(val message: String) : StockState()
}

private fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return dateFormat.format(Date())
}

// Data dummy untuk testing
val stocks = listOf(
    Stock(
        id = 0,
        nama = "Ikan Nila",
        jenis = "Ikan",
        kuantitas = 13,
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
    Stock(
        id = 1,
        nama = "Ikan Kerapu",
        jenis = "Ikan",
        kuantitas = 17,
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
            )
        )
    ),
    Stock(
        id = 2,
        nama = "Ikan Bawal",
        jenis = "Ikan",
        kuantitas = 21,
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
            )
        )
    ),
    Stock(
        id = 3,
        nama = "Ikan Tongkol",
        jenis = "Ikan",
        kuantitas = 15,
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
            )
        )
    ),
    Stock(
        id = 4,
        nama = "Sotong",
        jenis = "Ikan",
        kuantitas = 11,
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
            )
        )
    )
)