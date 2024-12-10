package com.app.amarine.model

// Request model untuk tambah catatan
data class AddNoteRequest(
    val id_nelayan: String,
    val nama: String,
    val jenis: String,
    val berat: String,
    val tanggal: String,
    val waktu: String,
    val lokasi_penyimpanan: String,
    val catatan: String?,
    val gambar: String?
)

// Response model untuk tambah catatan
data class AddNoteResponse(
    val status: Boolean,
    val message: String,
    val data: NoteData?
)

// Data model untuk detail catatan
data class NoteData(
    val id: Int,
    val id_nelayan: String,
    val nama: String,
    val jenis: String,
    val berat: String,
    val tanggal: String,
    val waktu: String,
    val lokasi_penyimpanan: String,
    val catatan: String?,
    val gambar: String?,
    val created_at: String,
    val updated_at: String
)

// BaseResponse wrapper yang sudah ada
data class BaseResponse<T>(
    val status: Boolean,
    val message: String,
    val data: T?
)