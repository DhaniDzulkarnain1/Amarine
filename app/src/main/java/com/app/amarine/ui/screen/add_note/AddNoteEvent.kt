package com.app.amarine.ui.screen.add_note

import android.content.Context
import android.net.Uri

sealed class AddNoteEvent {
    data class OnNameChange(val value: String) : AddNoteEvent()
    data class OnTypeChange(val value: String) : AddNoteEvent()
    data class OnWeightChange(val value: String) : AddNoteEvent()
    data class OnDateChange(val value: String) : AddNoteEvent()
    data class OnTimeChange(val value: String) : AddNoteEvent()
    data class OnStorageLocationChange(val value: String) : AddNoteEvent()
    data class OnNoteChange(val value: String) : AddNoteEvent()
    data class OnImageSelected(val uri: Uri?) : AddNoteEvent()
    data class OnShowImagePicker(val show: Boolean) : AddNoteEvent()
    data class OnShowPermissionDialog(val show: Boolean) : AddNoteEvent()
    data class OnPhotoUriChange(val uri: Uri?) : AddNoteEvent()
    data class OnSubmit(val context: Context) : AddNoteEvent()
}