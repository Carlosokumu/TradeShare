package com.example.smarttrader.di


import androidx.room.Room
import com.example.smarttrader.data.local.Database
import com.example.smarttrader.data.repository.UserRepo
import com.example.smarttrader.viewmodels.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val registerViewModel: Module = module {
    viewModel { RegisterViewModel(get(), get()) }
}


val positionsViewModel: Module = module {
    viewModel { PositionsViewModel(get()) }
}

val phoneRegisterViewModel: Module = module {
    viewModel { PhoneRegister(get()) }
}

val userViewModel: Module = module {
    viewModel { UserViewModel(get(),get()) }
}

val loginViewModel: Module = module {
    viewModel { LoginViewModel(get()) }
}

val emailAuthViewModel: Module = module {
    viewModel { EmailAuthViewModel(get()) }
}
val  mainViewModel: Module = module {
    viewModel { MainViewModel(get()) }
}
val  forumViewModel: Module = module {
    viewModel { ForumViewModel(get()) }
}

private val databaseModule: Module = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            "smart-trader-db"
        ).build()
    }
}

private val daoModule: Module = module {
    single { get<Database>().userDao() }

}
val  userRepo: Module = module {
    single { UserRepo(get()) }
}



val appModules: List<Module> = listOf(
    registerViewModel,
    positionsViewModel,
    phoneRegisterViewModel,
    databaseModule,
    daoModule,
    userRepo,
    userViewModel,
    loginViewModel,
    emailAuthViewModel,
    mainViewModel,
    forumViewModel
)
