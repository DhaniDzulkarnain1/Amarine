package com.app.amarine.model

import android.os.Parcelable
import com.app.amarine.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Int,
    val imageResourceId: Int,
    val name: String,
    val type: String,
    val weight: String,
    val date: String,
    val storageLocation: String,
    val note: String,
): Parcelable

val notes = listOf(
    Note(
        id = 0,
        imageResourceId = R.drawable.ic_note_1,
        name = "Kepiting",
        type = "Kepiting Galah",
        weight = "10 Kg",
        date = "8 November 2024",
        storageLocation = "Box 1",
        note = "Kepiting ini ditangkap di daerah laut sekitar Batu Merah"
    ),
    Note(
        id = 1,
        imageResourceId = R.drawable.ic_note_2,
        name = "Kerapu",
        type = "Kerapu Cantang",
        weight = "8 Kg",
        date = "10 November 2024",
        storageLocation = "Box 2",
        note = "Kerapu ini ditangkap di daerah laut sekitar Desa Kelong, Kepulauan Riau"
    ),
    Note(
        id = 2,
        imageResourceId = R.drawable.ic_note_3,
        name = "Kepiting",
        type = "Kepiting Biru",
        weight = "9 Kg",
        date = "11 November 2024",
        storageLocation = "Box 3",
        note = "Kepiting ini berasal dari laut di daerah Kalimantan Timur"
    )
)