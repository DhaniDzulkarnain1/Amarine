package com.app.amarine.ui.screen.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.amarine.ApiService
import com.app.amarine.SessionManager
import com.app.amarine.model.BaseResponse
import com.app.amarine.model.Note
import com.app.amarine.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _noteState = MutableStateFlow<Result<BaseResponse<List<Note>>>>(Result.Initial)
    val noteState: StateFlow<Result<BaseResponse<List<Note>>>> = _noteState.asStateFlow()

    private val _selectedNoteId = MutableStateFlow<Int?>(null)
    val selectedNoteId: StateFlow<Int?> = _selectedNoteId.asStateFlow()

    init {
        getNotes()
    }

    fun getNotes() {
        viewModelScope.launch {
            try {
                _noteState.value = Result.Loading

                val idNelayan = sessionManager.getNelayanId()

                if (idNelayan == 0) {
                    _noteState.value = Result.Error("ID Nelayan tidak valid")
                    return@launch
                }

                Log.d("DEBUG_NOTE", "Fetching notes for nelayan ID: $idNelayan")
                val response = apiService.getNotes(idNelayan)

                if (response.isSuccessful && response.body() != null) {
                    Log.d("DEBUG_NOTE", "Successfully fetched notes: ${response.body()}")
                    _noteState.value = Result.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("DEBUG_NOTE", "Failed to fetch notes: $errorBody")
                    _noteState.value = Result.Error(
                        errorBody?.let { "Gagal mengambil data catatan: $it" }
                            ?: "Gagal mengambil data catatan"
                    )
                }
            } catch (e: Exception) {
                Log.e("DEBUG_NOTE", "Exception while fetching notes: ${e.message}", e)
                _noteState.value = Result.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    fun getDetailNote(id: Int) {
        viewModelScope.launch {
            try {
                _noteState.value = Result.Loading

                Log.d("DEBUG_NOTE", "Fetching note detail for ID: $id")
                val response = apiService.getDetailNote(id)

                if (response.isSuccessful && response.body() != null) {
                    Log.d("DEBUG_NOTE", "Successfully fetched note detail: ${response.body()}")
                    // Handle detail note if needed
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("DEBUG_NOTE", "Failed to fetch note detail: $errorBody")
                    _noteState.value = Result.Error(
                        errorBody?.let { "Gagal mengambil detail catatan: $it" }
                            ?: "Gagal mengambil detail catatan"
                    )
                }
            } catch (e: Exception) {
                Log.e("DEBUG_NOTE", "Exception while fetching note detail: ${e.message}", e)
                _noteState.value = Result.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    fun selectNote(id: Int) {
        _selectedNoteId.value = id
    }

    fun clearSelectedNote() {
        _selectedNoteId.value = null
    }

    fun refreshNotes() {
        getNotes()
    }
}