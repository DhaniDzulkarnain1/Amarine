package com.app.amarine.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Int,
    val id_nelayan: Int,
    val nama: String,
    val jenis: String,
    val berat: Double,
    val tanggal: String,
    val waktu: String,
    val lokasi_penyimpanan: String,
    val catatan: String?,
    val gambar: String?
): Parcelable

data class Noted(
    @SerializedName("id_nelayan") val id_nelayan: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String,
    @SerializedName("storageLocation") val storageLocation: String,
    @SerializedName("noteType") val noteType: String
)

//data class AddNoteRequest(
//    val id_nelayan: Int,
//    val nama: String,
//    val jenis: String,
//    val berat: Double,
//    val tanggal: String,
//    val waktu: String,
//    val lokasi_penyimpanan: String,
//    val catatan: String?,
//    val gambar: String?
//)


// Data dummy untuk testing
val notes = listOf(
    Note(
        id = 0,
        id_nelayan = 1,
        nama = "Kepiting",
        jenis = "Kepiting Galah",
        berat = 10.0,
        tanggal = "2024-11-08",
        waktu = "10:00",
        lokasi_penyimpanan = "Box 1",
        catatan = "Kepiting ini ditangkap di daerah laut sekitar Batu Merah",
        gambar = null
    ),
    Note(
        id = 1,
        id_nelayan = 1,
        nama = "Kerapu",
        jenis = "Kerapu Cantang",
        berat = 8.0,
        tanggal = "2024-11-10",
        waktu = "14:30",
        lokasi_penyimpanan = "Box 2",
        catatan = "Kerapu ini ditangkap di daerah laut sekitar Desa Kelong, Kepulauan Riau",
        gambar = null
    ),
    Note(
        id = 2,
        id_nelayan = 1,
        nama = "Kepiting",
        jenis = "Kepiting Biru",
        berat = 9.0,
        tanggal = "2024-11-11",
        waktu = "09:15",
        lokasi_penyimpanan = "Box 3",
        catatan = "Kepiting ini berasal dari laut di daerah Kalimantan Timur",
        gambar = null
    )
)