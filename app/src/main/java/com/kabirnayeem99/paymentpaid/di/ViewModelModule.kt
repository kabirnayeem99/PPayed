package com.kabirnayeem99.paymentpaid.di

import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepositoryImpl
import com.kabirnayeem99.paymentpaid.domain.repositories.WorkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideWorkRepository(): WorkRepository {
        return WorkRepositoryImpl()
    }
}