package com.example.adataskplanner.di

import android.content.Context
import com.example.adataskplanner.BuildConfig
import com.example.adataskplanner.data.api.AdaTaskPlannerApi
import com.example.adataskplanner.data.token.JWTInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.adaschool.rest.storage.SharedPreferencesLocalStorage
import org.adaschool.rest.storage.TokenStorage
import org.adaschool.rest.utils.SHARED_PREFERENCES_FILE_NAME
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ActivityComponent::class)
class ApplicationModule {



    @Provides
    fun providesLocalStorage(@ApplicationContext context: Context): TokenStorage {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        return SharedPreferencesLocalStorage(sharedPreferences)
    }


    @Provides
    fun provideJWTInterceptor(tokenStorage: TokenStorage): JWTInterceptor{
        return JWTInterceptor(tokenStorage)
    }


    @Provides
    fun providesRetrofit(jwtInterceptor: JWTInterceptor): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(jwtInterceptor)
            .writeTimeout(0, TimeUnit.MILLISECONDS)
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES).build()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            .create()

        return Retrofit.Builder()
            .baseUrl("https://tasks-planner-api.herokuapp.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    fun providesAdaTaskPlannerApi(retrofit: Retrofit): AdaTaskPlannerApi{
        return retrofit.create(AdaTaskPlannerApi::class.java)
    }
}