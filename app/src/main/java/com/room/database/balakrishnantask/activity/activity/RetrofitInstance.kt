package com.room.database.balakrishnantask.activity.activity

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        this.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private var retrofit: Retrofit? = null
    private val okHttpClient = OkHttpClient.Builder().connectTimeout(200, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .readTimeout(200, TimeUnit.SECONDS).writeTimeout(200, TimeUnit.SECONDS)
    private val httpClient = OkHttpClient()
    private val builder = Retrofit.Builder()
        .baseUrl(BaseUrl.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())

    @dagger.Provides
    @Singleton
    fun getRetrofitInstance(): CallApi {
        okHttpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                requestBuilder.header("Accept", "application/json")
                return chain.proceed(requestBuilder.build())
            }

        })
        val gson = GsonBuilder()
            .setLenient()
            .create()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(okHttpClient.build())
                .baseUrl(BaseUrl.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit!!.create(CallApi::class.java)
    }

}



