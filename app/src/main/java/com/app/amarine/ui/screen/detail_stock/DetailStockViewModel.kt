package com.app.amarine.ui.screen.detail_stock

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.amarine.ApiService
import com.app.amarine.model.Stock
import com.app.amarine.model.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailStockViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    private val _stockDetail = MutableStateFlow<Stock?>(null)
    val stockDetail: StateFlow<Stock?> = _stockDetail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getStockDetail(nama: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("DetailStockViewModel", "Fetching detail for nama: $nama")
                val response = apiService.getStockDetail(nama)
                Log.d("DetailStockViewModel", "Response: ${response.body()}")

                if (response.isSuccessful && response.body()?.status == true) {
                    _stockDetail.value = response.body()?.data
                    Log.d("DetailStockViewModel", "Stock detail set: ${_stockDetail.value}")
                } else {
                    _error.value = response.body()?.message ?: "Server error: ${response.code()}"
                    Log.e("DetailStockViewModel", "Error: ${response.body()?.message}")
                }
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("DetailStockViewModel", "Error: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun clearStockDetail() {
        _stockDetail.value = null
    }

    fun retryFetch(nama: String) {
        clearError()
        getStockDetail(nama)
    }
}