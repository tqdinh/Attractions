package com.example.data.datasource

import com.example.data.ApiResult
import com.example.data.AttractionApi
import com.example.data.dto.AttractionRepone
import com.example.data.dto.DtoMapper
import com.example.data.entity.Attraction
import kotlinx.coroutines.delay
import javax.inject.Inject

class RemoteDatasource @Inject constructor(
    val api: AttractionApi, val mapper: DtoMapper<AttractionRepone, Attraction>
) {
    suspend fun getAttractions(lang: String, page: Int): ApiResult<Attraction> {

        return try {
            val ret = api.getAll(lang, page)
            if (ret.isSuccessful) {
                delay(500)
                val bodyDTO = ret.body()
                if (null != bodyDTO) {
                    val domain = mapper.toDomain(bodyDTO)
                    delay(500)
                    return ApiResult.Success(domain)
                } else {
                    return ApiResult.Error("Empty body")
                }
            } else {
                delay(500)
                val error = ret.errorBody()?.byteStream().toString()
                return ApiResult.Error(error)
            }
        } catch (e: Exception) {
            delay(500)
            return ApiResult.Error(e.toString())
        }


    }
}