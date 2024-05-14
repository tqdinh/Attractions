package com.example.data.datasource

import com.example.data.ApiResult
import com.example.data.AttractionApi
import com.example.data.dto.AttractionRepone
import com.example.data.dto.DtoMapper
import com.example.data.entity.Attraction
import javax.inject.Inject

class RemoteDatasource @Inject constructor(
    val api: AttractionApi, val mapper: DtoMapper<AttractionRepone, Attraction>
) {
    suspend fun getAttractions(lang: String, page: Int): ApiResult<Attraction> {
        return try {
            val ret = api.getAll(lang, page)
            if (ret.isSuccessful) {
                val bodyDTO = ret.body()
                if (null != bodyDTO) {
                    val domain = mapper.toDomain(bodyDTO)
                    return ApiResult.Success<Attraction>(domain)
                }
                else {
                    return ApiResult.Error("Success with empty data")
                }
            } else {
                val error = ret.errorBody().toString()
                return ApiResult.Error(error)
            }
        } catch (e: Exception) {
            return ApiResult.Error(e.toString())
        }


    }
}