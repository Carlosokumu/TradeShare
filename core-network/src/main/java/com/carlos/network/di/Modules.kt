package com.carlos.network.di

import com.carlos.network.BuildConfig
import com.carlos.network.api.MtApi
import com.carlos.network.api.TradeShareApi
import com.carlos.network.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit


val networkingModule: Module = module {

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = when (BuildConfig.BUILD_TYPE) {
            "release" -> HttpLoggingInterceptor.Level.NONE
            else -> HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    single(named(Constants.GolangBackend)) {

        val gson = GsonBuilder()
            .serializeNulls()
            .create()

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_GO)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(get())
            .build()
    }
    single(named(Constants.MetaTraderBackend)) {

        val gson = GsonBuilder()
            .serializeNulls()
            .create()

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_META_TRADER)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(get())
            .build()
    }
}


val tradeShareApiModule: Module = module {
    single<TradeShareApi> { get<Retrofit>(named(Constants.GolangBackend)).create() }
}

val mtApiModule: Module = module {
    single<MtApi> { get<Retrofit>(named(Constants.MetaTraderBackend)).create() }
}


val netWorkModules: List<Module> = listOf(networkingModule, tradeShareApiModule, mtApiModule)