package com.adriianoakino.btg.di.modules

import android.content.SharedPreferences
import com.adriianoakino.btg.data.network.webclient.CurrencyLayerWebClient
import com.adriianoakino.btg.data.repositories.CurrencyLayerRepository
import com.adriianoakino.btg.ui.viewmodels.ConversorViewModel
import com.adriianoakino.btg.utils.DEFAULT
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {


    single<CurrencyLayerWebClient> {
        CurrencyLayerWebClient()
    }

    single<CurrencyLayerRepository> {
        CurrencyLayerRepository(get())
    }

    single{
        androidApplication().getSharedPreferences(DEFAULT,  android.content.Context.MODE_PRIVATE)
    }

    single<SharedPreferences.Editor> {
        androidApplication().getSharedPreferences(DEFAULT,  android.content.Context.MODE_PRIVATE).edit()
    }

    viewModel<ConversorViewModel> { ConversorViewModel(get()) }

}
