package com.kabirnayeem99.paymentpaid.di

import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.data.repositories.AuthRepositoryImpl
import com.kabirnayeem99.paymentpaid.data.repositories.ChartRepositoryImpl
import com.kabirnayeem99.paymentpaid.data.sources.FirebaseAuthDataSource
import com.kabirnayeem99.paymentpaid.domain.repositories.AuthRepository
import com.kabirnayeem99.paymentpaid.domain.repositories.ChartRepository
import com.kabirnayeem99.paymentpaid.domain.sources.AuthDataSource
import com.kabirnayeem99.paymentpaid.presentation.fragments.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
@ExperimentalCoroutinesApi
object ActivityModule {

    @Provides
    @Singleton
    fun provideWorkFragment() = WorkFragment()

    @Provides
    @Singleton
    fun providePaymentsFragment() = PaymentsFragment()

    @Provides
    @Singleton
    fun provideAboutFragment() = AboutFragment()

    @Provides
    @Singleton
    fun provideAnalyticsFragment() = AnalyticsFragment()

    @Provides
    @Singleton
    fun provideProfileFragment() = ProfileFragment()

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideTimer(): Timer = Timer()



}