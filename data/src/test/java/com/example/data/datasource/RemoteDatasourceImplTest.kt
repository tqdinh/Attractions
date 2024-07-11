package com.example.data.datasource

import com.example.data.ApiResult
import com.example.data.AttractionApi
import com.example.data.dto.AttractionDTO
import com.example.data.dto.AttractionRepone
import com.example.data.dto.AttrationDtoMapper
import com.example.data.dto.DtoMapper
import com.example.data.dto.ImageDTO
import com.example.data.entity.Attraction
import com.example.data.entity.AttractionPlace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDatasourceImplTest {
    private lateinit var attractionApi: AttractionApi
    private lateinit var datasource: RemoteDatasource
    private lateinit var mapper: DtoMapper<AttractionRepone, Attraction>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        attractionApi = mock(AttractionApi::class.java)
        mapper = mock(AttrationDtoMapper::class.java)
        datasource = RemoteDatasource(attractionApi, mapper)

    }

    @Test
    fun getResponeSuccess() = runTest {
        val attractionDto0 = ImageDTO(src = "http://subdomain.abc.img0", "subjet", "jpg")
        val attractionDto1 = ImageDTO(src = "http://subdomain.abc.img1", "subjet", "jpg")
        val attractionDto2 = ImageDTO(src = "http://subdomain.abc.img2", "subjet", "jpg")
        val attractionDto3 = ImageDTO(src = "http://subdomain.abc.img3", "subjet", "jpg")
        val attractionDTO0 = AttractionDTO(
            0, "name", "intro", "official_website", "address", "20/5/2025",
            listOf(attractionDto0, attractionDto1, attractionDto2, attractionDto3)
        )
        val attractionDTO1 = AttractionDTO(
            1, "name", "intro", "official_website", "address", "20/5/2025",
            listOf(attractionDto0, attractionDto1, attractionDto2, attractionDto3)
        )
        val attractionDTO2 = AttractionDTO(
            2, "name", "intro", "official_website", "address", "20/5/2025",
            listOf(attractionDto0, attractionDto1, attractionDto2, attractionDto3)
        )

        val listDto = listOf(attractionDTO0, attractionDTO1, attractionDTO2)
        val total = listDto.size

        val mockResponeSucess: Response<AttractionRepone> = Response.success(
            AttractionRepone(
                total,
                listDto
            )
        )

        val attractionPlace0 = AttractionPlace(
            attractionDTO0.id,
            attractionDTO0.name,
            attractionDTO0.introduction,
            attractionDTO0.official_site,
            attractionDTO0.images.first().src,
            attractionDTO0.address,
            attractionDTO0.modified
        )

        val attractionPlace1 = AttractionPlace(
            attractionDTO1.id,
            attractionDTO1.name,
            attractionDTO1.introduction,
            attractionDTO1.official_site,
            attractionDTO1.images.first().src,
            attractionDTO1.address,
            attractionDTO1.modified
        )

        val attractionPlace2 = AttractionPlace(
            attractionDTO2.id,
            attractionDTO2.name,
            attractionDTO2.introduction,
            attractionDTO2.official_site,
            attractionDTO2.images.first().src,
            attractionDTO2.address,
            attractionDTO2.modified
        )

        val attractionPlaces = mutableListOf(attractionPlace0, attractionPlace1, attractionPlace2)
        val attraction = Attraction(total, attractionPlaces)

        `when`(attractionApi.getAll("vi", 1)).thenReturn(mockResponeSucess)
        `when`(mapper.toDomain(mockResponeSucess.body()!!)).thenReturn(attraction)
        val result = datasource.getAttractions("vi", 1) as ApiResult.Success<Attraction>

        assert(result is ApiResult.Success)
        assert(result.data.total == total)
        assert(result.data.data.size == attractionPlaces.size)

    }

    @Test
    fun getResponeError() = runTest {
        val mockResponseFail=Response.error<AttractionRepone>(400,ResponseBody.create(null,"not found"))
        `when`(attractionApi.getAll("vi", 1)).thenReturn(mockResponseFail)
        val result = datasource.getAttractions("vi", 1) as ApiResult.Error<*>

        assert(result is ApiResult.Error)
    }
}