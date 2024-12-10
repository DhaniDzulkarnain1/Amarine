//package com.app.amarine
//
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//
//object RetrofitClient {
//    // Opsi 1: Menggunakan localhost untuk emulator
//    // private const val BASE_URL = "http://10.0.2.2:3000"  // Hapus slash di akhir
//
//    // Opsi 2: Atau gunakan IP yang sama dengan Postman
//    private const val BASE_URL = "http://10.170.15.114:3000"
//
//    val instance: ApiService by lazy {
//        val loggingInterceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("$BASE_URL/") // Tambahkan slash di sini
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        retrofit.create(ApiService::class.java)
//    }
//}