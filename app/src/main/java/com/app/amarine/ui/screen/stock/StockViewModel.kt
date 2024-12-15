package com.app.amarine.ui.stock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.amarine.ApiService
import com.app.amarine.model.Stock
import com.app.amarine.model.StockResponse
import com.app.amarine.model.StockState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    private val _stockState = MutableStateFlow<StockState>(StockState.Loading)
    val stockState: StateFlow<StockState> = _stockState

    private val _stocks = MutableStateFlow<List<Stock>>(emptyList())
    val stocks: StateFlow<List<Stock>> = _stocks

    init {
        getStocks()
    }

    fun getStocks() {
        viewModelScope.launch {
            _stockState.value = StockState.Loading
            try {
                val response = apiService.getStocks()
                if (response.isSuccessful && response.body()?.status == true) {
                    // Konversi StockResponse ke Stock
                    val stockList = response.body()?.data?.map { stockResponse ->
                        Stock(
                            id = stockResponse.id ?: 0,
                            nama = stockResponse.nama,
                            jenis = stockResponse.jenis,
                            kuantitas = stockResponse.kuantitas,
                            tanggal = stockResponse.tanggal,
                            waktu = stockResponse.waktu,
                            lokasi_penyimpanan = stockResponse.lokasi_penyimpanan
                        )
                    } ?: emptyList()
                    _stocks.value = stockList
                    _stockState.value = StockState.Success(stockList)
                } else {
                    _stockState.value = StockState.Error(response.body()?.message ?: "Failed to load stocks")
                }
            } catch (e: Exception) {
                _stockState.value = StockState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}