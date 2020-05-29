package com.adriianoakino.btg

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import android.content.pm.InstrumentationInfo
import androidx.lifecycle.MutableLiveData
import com.adriianoakino.btg.data.models.ConverterModel
import com.adriianoakino.btg.data.models.CurrencyLayerModel
import com.adriianoakino.btg.data.repositories.CurrencyLayerRepository
import com.adriianoakino.btg.data.repositories.Resource
import com.adriianoakino.btg.ui.viewmodels.ConversorViewModel
import com.adriianoakino.btg.utils.ID_DESTINATION
import com.adriianoakino.btg.utils.ID_ORIGEM
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


class ConversorTest: KoinTest {

    val BRL = "BRL"
    val USD = "USD"
    val AMMOUNT = "1"

    @get:Rule
    val koinTestRule = KoinTestRule.create {  loadKoinModules(appModulesTest) }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz -> mock(clazz.java) }

    lateinit var instrumentationContext: Context

    @Before
    fun setUp() {

        instrumentationContext = mock(Context::class.java)
        MockitoAnnotations.initMocks(this)

    }

    @After
    fun close() { stopKoin() }


    val conversor : ConversorViewModel by inject()

    @Test
    fun `declareMock with KoinTest`() {

        val liveData = MutableLiveData<Resource<CurrencyLayerModel?>>()
        val origem = MutableLiveData<String>()


        declareMock<ConversorViewModel> {
            BDDMockito.given(this.getAllCurrencies(instrumentationContext)).willReturn(liveData)
            BDDMockito.given(this.getCurrencyOrigem()).willReturn(origem)
            BDDMockito.given(this.getCurrencyDestination()).willReturn(origem)
        }
    }

    @Test
    fun `declareMock with KoinTest and test setOrigemNavigation`() {


        declareMock<ConversorViewModel> {
            this.setNavigationId(ID_ORIGEM)
            BDDMockito.given(this.getNavigationId()).willReturn(ID_ORIGEM)
            this.setNavigationId(ID_DESTINATION)
            BDDMockito.given(this.getNavigationId()).willReturn(ID_DESTINATION)
        }
    }

    @Test
    fun `declareMock with KoinTest and test add Currencies show buttons`() {
        declareMock<ConversorViewModel> {
            this.setCurrencyDestination(BRL)
            BDDMockito.given(this.getNavigationId()).willReturn(BRL)
            this.setCurrencyOrigem(USD)
            BDDMockito.given(this.getNavigationId()).willReturn(USD)
        }
    }

    @Test
    fun `declareMock with KoinTest Repository and getCurrencies`() {
        declareMock<CurrencyLayerRepository> {
            val liveData = MutableLiveData<Resource<CurrencyLayerModel?>>()
            BDDMockito.given(this.getAllCurrencies(context = instrumentationContext)).willReturn(liveData)
        }
    }

    @Test
    fun `declareMock with KoinTest Repository and postConverter`() {
        declareMock<CurrencyLayerRepository> {
            val liveData = MutableLiveData<Resource<ConverterModel>>()
            BDDMockito.given(this.converter(BRL, USD, AMMOUNT)).willReturn(liveData)
        }
    }


}