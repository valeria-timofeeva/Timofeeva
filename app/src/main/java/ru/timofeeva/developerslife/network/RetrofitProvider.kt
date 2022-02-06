package ru.timofeeva.developerslife.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ru.timofeeva.developerslife.models.Post

object RetrofitProvider {
    private val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://developerslife.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createHttpClient())
            .build()
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    val developersLifeApi by lazy {
        retrofitInstance.create(DevelopersLifeService::class.java)
    }

    interface DevelopersLifeService {
        @GET("/random?json=true")
        fun getNextPost(): Call<Post>
    }
}