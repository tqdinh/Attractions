package com.example.data.entity

data class Attraction(
    var total: Int,
    val data: MutableList<AttractionPlace> = mutableListOf(),
)

