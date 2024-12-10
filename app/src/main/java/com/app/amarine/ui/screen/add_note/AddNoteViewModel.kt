package com.app.amarine.ui.screen.add_note

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.amarine.ApiService
import com.app.amarine.SessionManager
import com.app.amarine.model.AddNoteFormState
import com.app.amarine.model.BaseResponse
import com.app.amarine.model.Note
import com.app.amarine.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(AddNoteFormState())
    val state = _state.asStateFlow()

    private val _addNoteState = MutableStateFlow<Result<BaseResponse<Note>>>(Result.Initial)
    val addNoteState: StateFlow<Result<BaseResponse<Note>>> = _addNoteState

    fun onEvent(event: AddNoteEvent) {
        when (event) {
            is AddNoteEvent.OnNameChange -> {
                _state.update { it.copy(
                    name = event.value,
                    errors = it.errors - "name"
                ) }
            }
            is AddNoteEvent.OnTypeChange -> {
                _state.update { it.copy(
                    type = event.value,
                    errors = it.errors - "type"
                ) }
            }
            is AddNoteEvent.OnWeightChange -> {
                _state.update { it.copy(
                    weight = event.value,
                    errors = it.errors - "weight"
                ) }
            }
            is AddNoteEvent.OnDateChange -> {
                _state.update { it.copy(
                    date = event.value,
                    errors = it.errors - "date"
                ) }
            }
            is AddNoteEvent.OnTimeChange -> {
                _state.update { it.copy(
                    time = event.value,
                    errors = it.errors - "time"
                ) }
            }
            is AddNoteEvent.OnStorageLocationChange -> {
                _state.update { it.copy(
                    storageLocation = event.value,
                    errors = it.errors - "storageLocation"
                ) }
            }
            is AddNoteEvent.OnNoteChange -> {
                _state.update { it.copy(note = event.value) }
            }
            is AddNoteEvent.OnImageSelected -> {
                _state.update { it.copy(
                    selectedImageUri = event.uri,
                    errors = it.errors - "image"
                ) }
            }
            is AddNoteEvent.OnShowImagePicker -> {
                _state.update { it.copy(showImagePickerDialog = event.show) }
            }
            is AddNoteEvent.OnShowPermissionDialog -> {
                _state.update { it.copy(showPermissionDialog = event.show) }
            }
            is AddNoteEvent.OnPhotoUriChange -> {
                _state.update { it.copy(photoUri = event.uri) }
            }
            is AddNoteEvent.OnSubmit -> {
                submitNote(event.context)
            }
        }
    }

    private fun submitNote(context: Context) {
        val currentState = _state.value
        val errors = validateForm(currentState)

        if (errors.isEmpty()) {
            try {
                val weight = currentState.weight.toDoubleOrNull() ?: 0.0
                addNote(
                    context = context,
                    nama = currentState.name,
                    jenis = currentState.type,
                    berat = weight,
                    tanggal = currentState.date,
                    waktu = currentState.time,
                    lokasiPenyimpanan = currentState.storageLocation,
                    catatan = currentState.note.takeIf { it.isNotEmpty() },
                    imageUri = currentState.selectedImageUri
                )
            } catch (e: Exception) {
                _addNoteState.value = Result.Error("Terjadi kesalahan: ${e.message}")
            }
        } else {
            _state.update { it.copy(errors = errors) }
        }
    }

    private fun validateForm(state: AddNoteFormState): Map<String, Boolean> {
        return buildMap {
            put("name", state.name.isEmpty())
            put("type", state.type.isEmpty())
            put("weight", state.weight.isEmpty() || !state.weight.matches(Regex("^\\d*\\.?\\d*$")))
            put("date", state.date.isEmpty())
            put("time", state.time.isEmpty())
            put("storageLocation", state.storageLocation.isEmpty())
            put("image", state.selectedImageUri == null)
        }.filterValues { it }
    }

    private fun addNote(
        context: Context,
        nama: String,
        jenis: String,
        berat: Double,
        tanggal: String,
        waktu: String,
        lokasiPenyimpanan: String,
        catatan: String?,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            try {
                _addNoteState.value = Result.Loading
                _state.update { it.copy(isLoading = true) }

                val idAkun = sessionManager.getUserId()
                val idNelayan = sessionManager.getNelayanId()

                Log.d("DEBUG_NOTE", "Starting AddNote process")
                Log.d("DEBUG_NOTE", "IDs from SessionManager - idAkun: $idAkun, idNelayan: $idNelayan")

                if (idNelayan == 0) {
                    Log.e("DEBUG_NOTE", "Invalid Nelayan ID: $idNelayan")
                    _addNoteState.value = Result.Error("ID Nelayan tidak valid")
                    return@launch
                }

                if (idAkun == 0) {
                    Log.e("DEBUG_NOTE", "Invalid Akun ID: $idAkun")
                    _addNoteState.value = Result.Error("ID Akun tidak valid")
                    return@launch
                }

                val idNelayanBody = idNelayan.toString().toRequestBody("text/plain".toMediaType())
                val idAkunBody = idAkun.toString().toRequestBody("text/plain".toMediaType())
                val namaBody = nama.toRequestBody("text/plain".toMediaType())
                val jenisBody = jenis.toRequestBody("text/plain".toMediaType())
                val beratBody = berat.toString().toRequestBody("text/plain".toMediaType())
                val tanggalBody = tanggal.toRequestBody("text/plain".toMediaType())
                val waktuBody = waktu.toRequestBody("text/plain".toMediaType())
                val lokasiPenyimpananBody = lokasiPenyimpanan.toRequestBody("text/plain".toMediaType())
                val catatanBody = catatan?.toRequestBody("text/plain".toMediaType())

                Log.d("DEBUG_NOTE", "RequestBody created with data - nama: $nama, jenis: $jenis, berat: $berat")

                val gambarPart = imageUri?.let { uri ->
                    try {
                        context.contentResolver.openInputStream(uri)?.use { inputStream ->
                            val requestFile = inputStream.readBytes().toRequestBody("image/jpeg".toMediaType())
                            MultipartBody.Part.createFormData("gambar", "image.jpg", requestFile)
                        }
                    } catch (e: Exception) {
                        Log.e("DEBUG_NOTE", "Error processing image: ${e.message}", e)
                        null
                    }
                }

                Log.d("DEBUG_NOTE", "Sending request to server...")
                val response = withContext(Dispatchers.IO) {
                    apiService.addNote(
                        idNelayan = idNelayanBody,
                        idAkun = idAkunBody,
                        nama = namaBody,
                        jenis = jenisBody,
                        berat = beratBody,
                        tanggal = tanggalBody,
                        waktu = waktuBody,
                        lokasiPenyimpanan = lokasiPenyimpananBody,
                        catatan = catatanBody,
                        gambar = gambarPart
                    )
                }

                if (response.isSuccessful && response.body() != null) {
                    Log.d("DEBUG_NOTE", "Request successful: ${response.body()}")
                    _addNoteState.value = Result.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("DEBUG_NOTE", "Request failed with error: $errorBody")
                    _addNoteState.value = Result.Error(
                        errorBody?.let { "Gagal menambah catatan: $it" } ?: "Gagal menambah catatan"
                    )
                }
            } catch (e: Exception) {
                Log.e("DEBUG_NOTE", "Exception during addNote: ${e.message}", e)
                _addNoteState.value = Result.Error("Terjadi kesalahan: ${e.message}")
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}