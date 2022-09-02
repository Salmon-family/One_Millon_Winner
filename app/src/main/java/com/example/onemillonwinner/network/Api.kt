package com.example.onemillonwinner.network

import android.util.Log
import com.example.onemillonwinner.util.NetworkConstants.BASE_URL
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.xml.sax.ErrorHandler
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object Api {


    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(
            RxJava3CallAdapterFactory.createWithScheduler(
                Schedulers.io()
            )
        )
        .build()

    val triviaService: TriviaService = retrofit.create(TriviaService::class.java)
}

