package com.example.data.entity

data class Attraction(
    var total: Int,
    val data: MutableList<AttractionPlace> = mutableListOf(),

//    val name:String,
//    val name_zh:String,
//    val open_status:Int,
//    val introduction:String,
//    val open_time:String,
//    val zipcode:String,
//    val district:String,
//    val address:String,
//    val tel:String,
//    val fax:String,
//    val email:String,

)

data class AttractionPlace(
    val id: Int,
    val name: String,
    val introduction:String,
    val url:String,
    val avatar:String
)