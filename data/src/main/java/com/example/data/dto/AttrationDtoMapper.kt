package com.example.data.dto

import com.example.data.entity.Attraction
import com.example.data.entity.AttractionPlace

class AttrationDtoMapper : DtoMapper<AttractionRepone, Attraction> {
    override fun toDomain(dto: AttractionRepone): Attraction {
        val list_place = dto.data.map {
            var avatar = ""
            if (it.images.isNotEmpty()) {
                it.images.first()?.apply {
                    avatar = this.src
                }
            }
            AttractionPlace(
                it.id,
                it.name,
                it.introduction,
                it.official_site,
                avatar,
                it.address,
                it.modified
            )
        }.toMutableList()
        return Attraction(dto.total, list_place)

    }
}