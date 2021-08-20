package com.kabirnayeem99.paymentpaid.di

import com.kabirnayeem99.paymentpaid.data.sources.FirebaseDataSource
import com.kabirnayeem99.paymentpaid.domain.sources.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(): RemoteDataSource {
        return FirebaseDataSource()
    }
}