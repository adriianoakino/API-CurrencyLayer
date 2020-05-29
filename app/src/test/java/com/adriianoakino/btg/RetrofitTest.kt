package com.adriianoakino.btg

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.adriianoakino.btg.data.models.CurrencyLayerModel
import com.adriianoakino.btg.data.network.service.CurrencyLayerService
import com.adriianoakino.btg.data.network.webclient.CurrencyLayerWebClient
import com.adriianoakino.btg.data.repositories.CurrencyLayerRepository
import com.adriianoakino.btg.data.repositories.Resource
import org.junit.*
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


class RetrofitTest: KoinTest {


    @get:Rule
    val koinTestRule = KoinTestRule.create {
        loadKoinModules(appModulesTest)
    }



    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mock(clazz.java)
    }

    @Mock
    lateinit var client: CurrencyLayerWebClient

    @Mock
    lateinit var repository: CurrencyLayerRepository

    @Mock
    lateinit var service: CurrencyLayerService

    lateinit var instrumentationContext: Context


    @Before
     fun setUp() {
        instrumentationContext = mock(Context::class.java)
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun close() { stopKoin() }

    @Test
    fun `declareMock with KoinTest Repository and postConverter`() {
        declareMock<CurrencyLayerRepository> {

            val liveData = MutableLiveData<Resource<CurrencyLayerModel?>>()

            client.getAllCurrencies(
                    success = { exchangesRate -> exchangesRate?.let { liveData.postValue(Resource(it)) } },
                    error = { err -> liveData.postValue(Resource(data = null, error = err))})
            BDDMockito.given(this.getAllCurrencies(context = instrumentationContext)).willReturn(liveData)
        }
    }









}