package com.kabirnayeem99.paymentpaid.di

import com.kabirnayeem99.paymentpaid.data.repositories.ChartRepositoryImpl
import com.kabirnayeem99.paymentpaid.domain.repositories.ChartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {

    @Provides
    @Singleton
    fun provideChartRepository(): ChartRepository = ChartRepositoryImpl()
}