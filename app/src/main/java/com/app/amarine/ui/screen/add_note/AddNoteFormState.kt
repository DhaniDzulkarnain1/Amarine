package com.app.amarine.model

import android.net.Uri

data class AddNoteFormState(
    val name: String = "",
    val type: String = "",
    val weight: String = "",
    val date: String = "",
    val time: String = "",
    val storageLocation: String = "",
    val note: String = "",
    val selectedImageUri: Uri? = null,
    val photoUri: Uri? = null,
    val errors: Map<String, Boolean> = emptyMap(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showImagePickerDialog: Boolean = false,
    val showPermissionDialog: Boolean = false
) {
    fun isValid(): Boolean {
        val requiredFields = mapOf(
            "name" to name.isNotBlank(),
            "type" to type.isNotBlank(),
            "weight" to (weight.isNotBlank() && weight.matches(Regex("^\\d*\\.?\\d*$"))),
            "date" to date.isNotBlank(),
            "time" to time.isNotBlank(),
            "storageLocation" to storageLocation.isNotBlank(),
            "image" to (selectedImageUri != null)
        )

        return requiredFields.all { it.value }
    }

    fun withError(field: String): AddNoteFormState {
        return copy(
            errors = errors + (field to true)
        )
    }

    fun clearErrors(): AddNoteFormState {
        return copy(errors = emptyMap())
    }

    fun hasError(field: String): Boolean {
        return errors[field] == true
    }

    fun getErrorMessage(field: String): String? {
        return when {
            !hasError(field) -> null
            field == "name" -> "Nama tidak boleh kosong"
            field == "type" -> "Jenis tidak boleh kosong"
            field == "weight" -> when {
                weight.isEmpty() -> "Berat tidak boleh kosong"
                !weight.matches(Regex("^\\d*\\.?\\d*$")) -> "Masukkan berat yang valid"
                else -> null
            }
            field == "date" -> "Tanggal tidak boleh kosong"
            field == "time" -> "Waktu tidak boleh kosong"
            field == "storageLocation" -> "Lokasi penyimpanan tidak boleh kosong"
            field == "image" -> "Gambar tidak boleh kosong"
            else -> null
        }
    }

    fun setLoading(loading: Boolean): AddNoteFormState {
        return copy(isLoading = loading)
    }

    fun setErrorMessage(message: String?): AddNoteFormState {
        return copy(errorMessage = message)
    }

    fun showImagePicker(show: Boolean): AddNoteFormState {
        return copy(showImagePickerDialog = show)
    }

    fun showPermissionDialog(show: Boolean): AddNoteFormState {
        return copy(showPermissionDialog = show)
    }

    fun updateImage(uri: Uri?): AddNoteFormState {
        return copy(
            selectedImageUri = uri,
            errors = errors - "image"
        )
    }

    fun updatePhotoUri(uri: Uri?): AddNoteFormState {
        return copy(photoUri = uri)
    }

    companion object {
        fun validateField(field: String, value: String): Boolean {
            return when (field) {
                "name", "type", "date", "time", "storageLocation" -> value.isNotBlank()
                "weight" -> value.isNotBlank() && value.matches(Regex("^\\d*\\.?\\d*$"))
                else -> true
            }
        }
    }
}