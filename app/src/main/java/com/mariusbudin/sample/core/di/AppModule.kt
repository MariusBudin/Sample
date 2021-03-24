package com.mariusbudin.sample.core.di

import android.content.Context
import com.mariusbudin.sample.BuildConfig
import com.mariusbudin.sample.core.data.local.AppDatabase
import com.mariusbudin.sample.features.characters.data.local.CharacterDao
import com.mariusbudin.sample.features.characters.data.remote.CharactersRemoteDataSource
import com.mariusbudin.sample.features.characters.data.remote.CharactersService
import com.mariusbudin.sample.features.characters.data.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val API_BASE_URL = "https://rickandmortyapi.com/api/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(createClient())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharactersService =
        retrofit.create(CharactersService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(service: CharactersService) =
        CharactersRemoteDataSource(service)

    @Singleton
    @Provides
    fun provideCharacterRepository(
        remoteDataSource: CharactersRemoteDataSource,
        db: AppDatabase
    ) = CharactersRepository(remoteDataSource, db)

}