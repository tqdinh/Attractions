package com.example.data.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.ApiResult
import com.example.data.datasource.RemoteDatasource
import com.example.data.entity.Attraction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

interface AttractionRepository {
    suspend fun getListAttraction(lang: String, page: Int)
    suspend fun getMoreAttraction()
    suspend fun setLanguage(lang: String)

    fun getMyListAttraction(lang: String, page: Int): Flow<ApiResult<Attraction>>

}

class AttractionRepositoryImpl @Inject constructor(val remote: RemoteDatasource) :
    AttractionRepository {


//    private val _attraction: MutableLiveData<Attraction> =
//        MutableLiveData()
//    val attraction: LiveData<Attraction> = _attraction


//    private val _loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
//    val loading: LiveData<Boolean> = _loading
//
//    private val _error: MutableLiveData<String> = MutableLiveData<String>("")
//    val error: LiveData<String> = _error


    private var currentPage = 0
    private var currentLang = "vi"
    override suspend fun getListAttraction(lang: String, page: Int) {

//        currentPage = page
//        currentLang = lang
//        _loading.value = true
//        val ret = remote.getAttractions(lang, page)
//
//        if (ret is ApiResult.Success) {
//            var tmp = attraction.value
//            if (null == tmp) {
//                tmp = ret.data
//            } else {
//                tmp.data.addAll(ret.data.data)
//            }
//            withContext(Dispatchers.Main)
//            {
//                _attraction.value = tmp
//            }
//            _attraction.value = tmp
//        } else {
//
//            _error.value = (ret as ApiResult.Error<String>).data
//        }
//
//        _loading.value = false

    }

    override suspend fun getMoreAttraction() {
//        _attraction.value?.apply {
//            if (this.total > this.data.size) {
//                getListAttraction(currentLang, currentPage + 1)
//            }
//        }
    }

    override suspend fun setLanguage(lang: String) {
        currentLang = lang
        currentPage = 1
//        _list .value = Attraction(0, mutableListOf())
        getListAttraction(currentLang, currentPage)
    }

    override fun getMyListAttraction(lang: String, page: Int): Flow<ApiResult<Attraction>> = flow {

        currentPage = page
        currentLang = lang
        delay(500)
        emit(ApiResult.Loading(true))
        val ret = remote.getAttractions(lang, page)
        // val ret1= remote1.getSomthing....
        // merge sothing herer
        delay(500)
        emit(ApiResult.Loading(false))
        emit(ret)

    }


}