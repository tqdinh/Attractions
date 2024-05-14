package com.example.data.dto

interface DtoMapper<DTO, DOMAIN> {
    fun toDomain(dto: DTO): DOMAIN
}