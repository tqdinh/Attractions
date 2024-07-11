package com.example.attractions.fragment.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ApiResult
import com.example.data.entity.Attraction
import com.example.data.repository.AttractionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val attractionRepository: AttractionRepository) :
    ViewModel() {

    init {
        val currentLocale = Locale.getDefault().toString()
        getListAttraction(currentLocale, 1)

    }


    val _listAttraction: MutableLiveData<Attraction> = MutableLiveData()
    val listAttraction: LiveData<Attraction> = _listAttraction

    val _loading: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val loading: SharedFlow<Boolean> = _loading

    val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: SharedFlow<String> = _error

    fun getListAttraction(lang: String, page: Int) {

        viewModelScope.launch {
            supervisorScope {
                attractionRepository.getMyListAttraction(lang, page).flowOn(Dispatchers.IO)
                    .collect({
                        when (it) {
                            is ApiResult.Success -> {
                                _listAttraction.value = it.data

                            }

                            is ApiResult.Error<*> -> {
                                _error.emit(it.data as String)
                            }

                            is ApiResult.Loading -> {
                                _loading.emit(it.data)
                            }

                            else
                            -> {

                            }
                        }
                    })
            }
        }

    }

    fun getMoreAttraction() {
        viewModelScope.launch {
            attractionRepository.getMoreAttraction()
        }
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            supervisorScope {
                attractionRepository.getMyListAttraction(lang, 1).flowOn(Dispatchers.IO)
                    .collect({
                        when (it) {
                            is ApiResult.Success -> {
                                _listAttraction.value = it.data

                            }

                            is ApiResult.Error<*> -> {
                                _error.emit(it.data as String)
                            }

                            is ApiResult.Loading -> {
                                _loading.emit(it.data)
                            }

                            else
                            -> {

                            }
                        }
                    })
            }
        }
    }

}