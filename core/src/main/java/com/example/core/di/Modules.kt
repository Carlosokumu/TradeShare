package com.example.core.di

import android.app.Application
import com.example.core.BuildConfig
import com.example.core.network.data.api.EconomicCalendarService
import com.example.core.network.repository.*
import com.example.core.utils.Constants
import com.google.gson.GsonBuilder
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.messageadapter.protobuf.ProtobufMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    single(named(Constants.NodeBackend)) {

        val gson = GsonBuilder()
            .serializeNulls()
            .create()

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_NODE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(get())
            .build()
    }


    single(named(Constants.CTrader_WS)) {
        val lifecycle = AndroidLifecycle.ofApplicationForeground(androidContext() as Application)
        Scarlet.Builder()
            .webSocketFactory(get<OkHttpClient>().newWebSocketFactory(Constants.CTrader_Websocket))
            .lifecycle(lifecycle)
            .addMessageAdapterFactory(ProtobufMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()
    }



    single(named(Constants.ChatWs)) {
        val lifecycle = AndroidLifecycle.ofApplicationForeground(androidContext() as Application)
        Scarlet.Builder()
            .webSocketFactory(get<OkHttpClient>().newWebSocketFactory(Constants.EchoUrl))
            .lifecycle(lifecycle)
            .addMessageAdapterFactory(GsonMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()
    }
    single(named(Constants.ECONOMIC_CALENDER)) {
        val lifecycle = AndroidLifecycle.ofApplicationForeground(androidContext() as Application)
        Scarlet.Builder()
            .webSocketFactory(get<OkHttpClient>().newWebSocketFactory(Constants.ECONOMIC_CALENDAR_URL))
            .lifecycle(lifecycle)
            .addMessageAdapterFactory(GsonMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()
    }

}


val economicCalendarService = module {
    single<EconomicCalendarService> { get<Scarlet>(named(Constants.ECONOMIC_CALENDER)).create() }
}


val economicCalendarRepositoryModule: Module = module {
    single<EconomicCalendarRepo> { EconomicCalendarImpl(get()) }
}


val coreModules: List<Module> = listOf(
    networkingModule,
    economicCalendarService,
    economicCalendarRepositoryModule,
    economicCalendarService
)
