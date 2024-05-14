package com.example.data.di

import com.example.data.AttractionApi
import com.example.data.NetworkUtils
import com.example.data.datasource.RemoteDatasource
import com.example.data.dto.AttractionRepone
import com.example.data.dto.AttrationDtoMapper
import com.example.data.dto.DtoMapper
import com.example.data.entity.Attraction
import com.example.data.repository.AttractionRepository
import com.example.data.repository.AttractionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleProvider {
    @Provides
    @Singleton
    fun provideRepository(remoteDatasource: RemoteDatasource): AttractionRepository {
        return AttractionRepositoryImpl(remoteDatasource)
    }

    @Provides
    @Singleton
    fun provideAttractionApi(): AttractionApi {
        return NetworkUtils.getRetrofit().create(AttractionApi::class.java)
    }


    @Provides
    @Singleton
    fun provideAttractionDtoMapper(): DtoMapper<AttractionRepone, Attraction> {
        return AttrationDtoMapper()

    }


}