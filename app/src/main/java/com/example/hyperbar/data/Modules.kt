package com.example.hyperbar.data

import Repository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single {
        Repository()
    }
}

val dataModule = module {
    viewModel {
        DataModel(get())
    }
}