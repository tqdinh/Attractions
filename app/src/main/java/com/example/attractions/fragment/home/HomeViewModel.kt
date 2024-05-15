package com.example.attractions.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entity.Attraction
import com.example.data.entity.AttractionPlace
import com.example.data.repository.AttractionRepository
import com.example.data.repository.AttractionRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val attractionRepository: AttractionRepository) :
    ViewModel() {
    val listAttraction: LiveData<Attraction> =
        (attractionRepository as AttractionRepositoryImpl).attraction


    val loading: LiveData<Boolean> = (attractionRepository as AttractionRepositoryImpl).loading


    fun getListAttraction(lang: String, page: Int) {
        viewModelScope.launch {
            Log.d("GETMORE","getList")
            attractionRepository.getListAttraction(lang, page)
        }

    }

    fun getMoreAttraction() {
        viewModelScope.launch {
            attractionRepository.getMoreAttraction()
        }
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            attractionRepository.setLanguage(lang)
        }
    }

}