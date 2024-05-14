package com.example.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class AttractionRepone(
    @SerializedName("total")
    val total: Int,
    @SerializedName("data")
    val data: List<AttractionDTO>


)


data class AttractionDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("url")
    val url: String,

    @SerializedName("images")
    val images: List<ImageDTO>,
)

data class ImageDTO(
    @SerializedName("src")
    val src: String,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("ext")
    val ext: String,
)
