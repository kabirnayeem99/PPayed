package com.kabirnayeem99.paymentpaid.di

import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.data.sources.FirebaseAuthDataSource
import com.kabirnayeem99.paymentpaid.data.sources.FirebaseDataSource
import com.kabirnayeem99.paymentpaid.domain.sources.AuthDataSource
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
    fun provideRemoteDataSource(): RemoteDataSource {
        return FirebaseDataSource()
    }

    @Provides
    fun provideAuthDataSource(): AuthDataSource = FirebaseAuthDataSource()

    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

}