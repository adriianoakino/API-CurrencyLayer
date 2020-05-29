package com.adriianoakino.btg

import com.adriianoakino.btg.data.network.webclient.CurrencyLayerWebClient
import com.adriianoakino.btg.data.repositories.CurrencyLayerRepository
import com.adriianoakino.btg.ui.viewmodels.ConversorViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModulesTest = module {


    single<CurrencyLayerWebClient> {
        CurrencyLayerWebClient()
    }

    single<CurrencyLayerRepository> {
        CurrencyLayerRepository(get())
    }

    viewModel<ConversorViewModel> { ConversorViewModel(get()) }

}
