package com.app.amarine

import com.app.amarine.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("akun")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("masuk")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @Multipart
    @POST("pencatatan")
    suspend fun addNote(
        @Part("id_nelayan") idNelayan: RequestBody,
        @Part("id_akun") idAkun: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("jenis") jenis: RequestBody,
        @Part("berat") berat: RequestBody,
        @Part("tanggal") tanggal: RequestBody,
        @Part("waktu") waktu: RequestBody,
        @Part("lokasi_penyimpanan") lokasiPenyimpanan: RequestBody,
        @Part("catatan") catatan: RequestBody?,
        @Part gambar: MultipartBody.Part?
    ): Response<BaseResponse<Note>>

    @GET("pencatatan/{id_nelayan}")
    suspend fun getNotes(@Path("id_nelayan") idNelayan: Int): Response<BaseResponse<List<Note>>>

    @GET("pencatatan/detail/{id}")
    suspend fun getDetailNote(@Path("id") id: Int): Response<BaseResponse<Note>>

    @Multipart
    @PUT("pencatatan/{id}")
    suspend fun updateNote(
        @Path("id") id: Int,
        @Part("nama") nama: RequestBody,
        @Part("jenis") jenis: RequestBody,
        @Part("berat") berat: RequestBody,
        @Part("tanggal") tanggal: RequestBody,
        @Part("waktu") waktu: RequestBody,
        @Part("lokasi_penyimpanan") lokasiPenyimpanan: RequestBody,
        @Part("catatan") catatan: RequestBody?,
        @Part gambar: MultipartBody.Part?
    ): Response<BaseResponse<Note>>

    @DELETE("pencatatan/{id}")
    suspend fun deleteNote(@Path("id") id: Int): Response<BaseResponse<Unit>>
}