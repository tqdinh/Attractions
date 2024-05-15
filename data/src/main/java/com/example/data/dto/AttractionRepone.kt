package com.example.data.dto

import com.google.gson.annotations.SerializedName


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
    @SerializedName("official_site")
    val official_site: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("modified")
    val modified: String,



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
