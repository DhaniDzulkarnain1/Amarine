package com.app.amarine.detail_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.amarine.ApiService
import com.app.amarine.model.Note
import com.app.amarine.model.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailNoteViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _noteState = MutableStateFlow<Note?>(null)
    val noteState = _noteState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _deleteNoteState = MutableStateFlow<Result<BaseResponse<Unit>>>(Result.Initial)
    val deleteNoteState = _deleteNoteState.asStateFlow()

    fun getDetailNote(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null // Reset error state
            try {
                val response = apiService.getDetailNote(id)
                if (response.isSuccessful && response.body()?.status == true) {
                    _noteState.value = response.body()?.data
                } else {
                    _error.value = response.body()?.message ?: "Terjadi kesalahan saat mengambil detail catatan"
                }
            } catch (e: Exception) {
                _error.value = "Gagal mengambil detail catatan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            _deleteNoteState.value = Result.Loading
            try {
                val response = apiService.deleteNote(id)
                if (response.isSuccessful && response.body()?.status == true) {
                    _deleteNoteState.value = Result.Success(response.body()!!)
                } else {
                    _deleteNoteState.value = Result.Error(
                        response.body()?.message ?: "Gagal menghapus catatan"
                    )
                }
            } catch (e: Exception) {
                _deleteNoteState.value = Result.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    fun resetError() {
        _error.value = null
    }

    fun clearNote() {
        _noteState.value = null
    }

    sealed class Result<out T> {
        object Initial : Result<Nothing>()
        object Loading : Result<Nothing>()
        data class Success<T>(val data: T) : Result<T>()
        data class Error(val message: String) : Result<Nothing>()
    }
}