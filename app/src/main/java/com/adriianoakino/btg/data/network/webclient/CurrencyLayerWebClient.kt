package com.adriianoakino.btg.data.network.webclient

import com.adriianoakino.btg.data.models.ConverterModel
import com.adriianoakino.btg.data.models.CurrencyLayerModel
import com.adriianoakino.btg.data.network.RetrofitClient
import com.adriianoakino.btg.data.network.service.CurrencyLayerService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val REQUEST_ERROR = "Requisição não sucedida"
private const val ACCESS_KEY = "2b3016266ea3bb43a5ff14546026daea"

class CurrencyLayerWebClient (
    private val service: CurrencyLayerService = RetrofitClient().currencyLayerService
) {

    private fun <T> executeRequest(call: Call<T>, success: (ok: T?) -> Unit, error: (erro: String?) -> Unit) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    error(REQUEST_ERROR)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                error(t.message)
            }
        })
    }

    fun getAllCurrencies(success: (exchangesRate: CurrencyLayerModel?) -> Unit, error: (error: String?) -> Unit) {
        executeRequest(
            service.getAllExchangesRate(ACCESS_KEY),
            success,
            error
        )
    }

    fun getConverter(currency: String, source: String, format: String, success: (converter: ConverterModel?) -> Unit, error: (error: String?) -> Unit) {
        executeRequest(
            service.getConverter(ACCESS_KEY, currency, source, format),
            success,
            error

        )
    }

}