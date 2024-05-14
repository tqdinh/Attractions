package com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.ApiResult
import com.example.data.datasource.RemoteDatasource
import com.example.data.entity.Attraction
import javax.inject.Inject

interface AttractionRepository {
    suspend fun getListAttraction(lang: String, page: Int)
    suspend fun getMoreAttraction()
    suspend fun setLanguage(lang: String)

}

class AttractionRepositoryImpl @Inject constructor(val remote: RemoteDatasource) :
    AttractionRepository {


    private val _attraction: MutableLiveData<Attraction> =
        MutableLiveData()
    val attraction: LiveData<Attraction> = _attraction


    private val _loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private var currentPage = 0
    private var currentLang = "vi"
    override suspend fun getListAttraction(lang: String, page: Int) {

        currentPage = page
        currentLang = lang
        _loading.value = true
        val ret = remote.getAttractions(lang, page)

        if (ret is ApiResult.Success) {
            var tmp = attraction.value
            if (null == tmp) {
                tmp = ret.data
            } else {
                tmp.data.addAll(ret.data.data)
            }
            _attraction.value = tmp
        }
        _loading.value = false

    }

    override suspend fun getMoreAttraction() {
        _attraction.value?.apply {
            if (this.total > this.data.size) {
                getListAttraction(currentLang, currentPage + 1)
            }
        }
    }

    override suspend fun setLanguage(lang: String) {
        currentLang = lang
        currentPage = 1
        _attraction.value = Attraction(1, mutableListOf())
        getListAttraction(currentLang, currentPage)
    }


}