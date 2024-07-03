package com.android.swingwizards.di


import com.android.swingwizards.data.repository.UserRepo
import com.android.swingwizards.viewmodels.HomeViewModel
import com.android.swingwizards.viewmodels.MetaTraderViewModel
import com.android.swingwizards.viewmodels.ProfileScreenViewModel
import com.android.swingwizards.viewmodels.SignInViewModel
import com.android.swingwizards.viewmodels.SignUpProcessViewModel
import com.android.swingwizards.viewmodels.SignUpViewModel
import com.android.swingwizards.viewmodels.TradeHistoryViewModel
import com.android.swingwizards.viewmodels.TradersViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val homeViewModel: Module = module {
    viewModel { HomeViewModel(get(), get()) }
}


val profileScreenViewModel: Module = module {
    viewModel { ProfileScreenViewModel(get()) }
}


val signUpViewModel: Module = module {
    viewModel { SignUpViewModel(get(), get()) }
}


val signUpProcessViewModel: Module = module {
    viewModel { SignUpProcessViewModel(get(), get()) }
}


val signInViewModel: Module = module {
    viewModel { SignInViewModel(get(), get()) }
}

val metaTraderViewModel: Module = module {
    viewModel { MetaTraderViewModel(get(), get()) }
}


val tradersViewModel: Module = module {
    viewModel { TradersViewModel(get(), get()) }
}

val tradeHistoryViewModel: Module = module {
    viewModel { TradeHistoryViewModel(get(), get(), get()) }
}


val userRepo: Module = module {
    single { androidApplication().applicationContext }

    single { UserRepo(get()) }
}


val appModules: List<Module> = listOf(
    homeViewModel,
    signUpViewModel,
    signInViewModel,
    profileScreenViewModel,
    tradersViewModel,
    userRepo,
    signUpProcessViewModel,
    metaTraderViewModel,
    tradeHistoryViewModel,
)
