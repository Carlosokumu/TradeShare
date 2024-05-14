package com.carlos.data.di

import com.carlos.data.repositories.TradingAccountRepo
import com.carlos.data.repositories.TradingAccountRepoImpl
import com.carlos.data.repositories.UserRepository
import com.carlos.data.repositories.UserRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module


val userRepositoryModule: Module = module {
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
}


val tradingAccountRepoModule: Module = module {
    single<TradingAccountRepo> { TradingAccountRepoImpl(get()) }
}

val coreDataModules: List<Module> = listOf(
    userRepositoryModule,
    tradingAccountRepoModule
)