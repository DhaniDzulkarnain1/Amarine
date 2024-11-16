package com.app.amarine.model

import android.os.Parcelable
import com.app.amarine.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Member(
    val id: Long,
    val imageResourceId: Int,
    val name: String,
    val birthDate: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val status: String
) : Parcelable

val member = listOf(
    Member(
        id = 1,
        imageResourceId = R.drawable.ic_profile_1,
        name = "Burhan",
        birthDate = "17 Juni 1993",
        phoneNumber = "082215456987",
        email = "burhankeren@gmail.com",
        address = "Riau",
        status = "Pengepul"
    ),
    Member(
        id = 2,
        imageResourceId = R.drawable.ic_profile_2,
        name = "Bahrul",
        birthDate = "02 Oktober 1989",
        phoneNumber = "085642875902",
        email = "yantobandung@gmail.com",
        address = "Bandung",
        status = "Nelayan"
    ),
    Member(
        id = 3,
        imageResourceId = R.drawable.ic_profile_3,
        name = "Slamet Yahya",
        birthDate = "17 Agustus 1995",
        phoneNumber = "081227563890",
        email = "slametyahya2@gmail.com",
        address = "Nusa Tenggara Timur",
        status = "Nelayan"
    ),
    Member(
        id = 4,
        imageResourceId = R.drawable.ic_profile_4,
        name = "Mislam",
        birthDate = "20 Juli 1993",
        phoneNumber = "087732564870",
        email = "mislammm@gmail.com",
        address = "Kalimantan Timur",
        status = "Nelayan"
    ),
    Member(
        id = 5,
        imageResourceId = R.drawable.ic_profile_5,
        name = "Udin",
        birthDate = "18 Desember 1998",
        phoneNumber = "085587878900",
        email = "udinnnn102@gmail.com",
        address = "Kediri",
        status = "Nelayan"
    ),
)