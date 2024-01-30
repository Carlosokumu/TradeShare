package com.example.core.di

import android.app.Application
import com.example.core.BuildConfig

import com.example.core.network.data.api.CTraderWebsocketService
import com.example.core.network.data.api.MtApi
import com.example.core.network.data.api.TradeShareApi
import com.example.core.network.data.api.WebSocketService
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


    single(named(Constants.CTrader_WS)){
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

}


val apiModule: Module = module {
    single<TradeShareApi> { get<Retrofit>(named(Constants.GolangBackend)).create() }

}
val mtModule: Module = module {
    single<MtApi> { get<Retrofit>(named(Constants.NodeBackend)).create() }

}

val webSocketService = module {
    single<WebSocketService> { get<Scarlet>(named(Constants.ChatWs)).create() }
}

val cTraderWebsocketService = module {
    single<CTraderWebsocketService> { get<Scarlet>(named(Constants.CTrader_WS)).create() }
}



val userRepositoryModule: Module = module {
    single<UserRepository> { UserRepositoryImp(get(),get()) }
}

val  openPositionsRepoModule: Module = module {
    single<OpenPositionsRepo> { OpenPositionsRepoImp(get(),get()) }
}

val  chatRepositoryModule: Module = module {
    single<ChatRepository> { ChatRepositoryImp(get())}
}


val  cTraderRepositoryModule: Module = module {
    single<CTraderRepository> { CTraderRepositoryImpl(get())}
}

val coreModules: List<Module> = listOf(
    networkingModule,
    apiModule,
    userRepositoryModule,
    openPositionsRepoModule,
    mtModule,
    webSocketService,
    chatRepositoryModule,
    cTraderWebsocketService,
    cTraderRepositoryModule
)
