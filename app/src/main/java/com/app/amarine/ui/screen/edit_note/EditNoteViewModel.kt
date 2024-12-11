package com.app.amarine.edit_note

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.amarine.ApiService
import com.app.amarine.model.BaseResponse
import com.app.amarine.model.Note
import com.app.amarine.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val apiService: ApiService,
    private val contentResolver: ContentResolver
) : ViewModel() {

    private val _editNoteState = MutableStateFlow<Result<BaseResponse<Note>>>(Result.Initial)
    val editNoteState = _editNoteState.asStateFlow()

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri = _imageUri.asStateFlow()

    fun updateImage(uri: Uri?) {
        _imageUri.value = uri
    }

    fun updateNote(
        id: Int,
        nama: String,
        jenis: String,
        berat: String,
        tanggal: String,
        waktu: String,
        lokasiPenyimpanan: String,
        catatan: String,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            try {
                _editNoteState.value = Result.Loading
                Log.d("EditNoteViewModel", "Starting update note with id: $id")

                // Format tanggal ke YYYY-MM-DD
                val formattedTanggal = try {
                    // Mari ubah ini karena tanggal yang kita terima sekarang dalam format "dd MMM yyyy"
                    val inputFormat = SimpleDateFormat("dd MMM yyyy", Locale("id"))
                    val date = inputFormat.parse(tanggal)
                    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                    outputFormat.format(date!!)
                } catch (e: Exception) {
                    Log.e("EditNoteViewModel", "Error formatting date: $tanggal", e)
                    tanggal
                }

                // Prepare multipart data
                val namaBody = nama.toRequestBody(MultipartBody.FORM)
                val jenisBody = jenis.toRequestBody(MultipartBody.FORM)
                val beratBody = berat.toRequestBody(MultipartBody.FORM)
                val tanggalBody = formattedTanggal.toRequestBody(MultipartBody.FORM)
                val waktuBody = waktu.toRequestBody(MultipartBody.FORM)
                val lokasiBody = lokasiPenyimpanan.toRequestBody(MultipartBody.FORM)
                val catatanBody = catatan.toRequestBody(MultipartBody.FORM)

                // Handle image
                val imagePart = imageUri?.let { uri ->
                    createImageMultipart(uri)
                }

                // Log request data
                Log.d("EditNoteViewModel", "Sending update request with data: " +
                        "nama=$nama, jenis=$jenis, berat=$berat, " +
                        "tanggal=$formattedTanggal, waktu=$waktu, " +
                        "lokasi=$lokasiPenyimpanan, image=${imageUri != null}")

                val response = apiService.updateNote(
                    id = id,
                    nama = namaBody,
                    jenis = jenisBody,
                    berat = beratBody,
                    tanggal = tanggalBody,
                    waktu = waktuBody,
                    lokasiPenyimpanan = lokasiBody,
                    catatan = catatanBody,
                    gambar = imagePart
                )

                Log.d("EditNoteViewModel", "Response: ${response.isSuccessful}, Body: ${response.body()}")

                if (response.isSuccessful && response.body()?.status == true) {
                    _editNoteState.value = Result.Success(response.body()!!)
                } else {
                    _editNoteState.value = Result.Error(
                        response.body()?.message ?: "Gagal mengupdate catatan"
                    )
                }
            } catch (e: Exception) {
                Log.e("EditNoteViewModel", "Error updating note", e)
                _editNoteState.value = Result.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    private fun createImageMultipart(uri: Uri): MultipartBody.Part? {
        return try {
            // Get input stream from URI
            contentResolver.openInputStream(uri)?.use { inputStream ->
                // Create temp file
                val fileExtension = MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(contentResolver.getType(uri)) ?: "jpg"
                val tempFile = File.createTempFile("image", ".$fileExtension")

                // Write input stream to temp file
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }

                // Create MultipartBody.Part from file
                val requestFile = tempFile.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData(
                    "gambar",
                    "image.$fileExtension",
                    requestFile
                )
            }
        } catch (e: Exception) {
            Log.e("EditNoteViewModel", "Error creating image multipart", e)
            null
        }
    }

    fun resetState() {
        _editNoteState.value = Result.Initial
        _imageUri.value = null
    }
}