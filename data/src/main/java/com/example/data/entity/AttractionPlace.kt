package com.example.data.entity

import java.io.Serializable


data class AttractionPlace(
    val id: Int,
    val name: String,
    val introduction: String,
    val official_site: String,
    val avatar: String,
    val address:String,
    val modified:String
) : Serializable