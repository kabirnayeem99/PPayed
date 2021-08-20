package com.kabirnayeem99.paymentpaid.di

import com.kabirnayeem99.paymentpaid.data.repositories.AuthRepositoryImpl
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepositoryImpl
import com.kabirnayeem99.paymentpaid.domain.repositories.AuthRepository
import com.kabirnayeem99.paymentpaid.domain.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.domain.sources.AuthDataSource
import com.kabirnayeem99.paymentpaid.domain.sources.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideWorkRepository(dataSource: RemoteDataSource): WorkRepository {
        return WorkRepositoryImpl(dataSource)
    }

    @Provides
    fun provideAuthRepository(dataSource: AuthDataSource): AuthRepository {
        return AuthRepositoryImpl(dataSource)
    }
}