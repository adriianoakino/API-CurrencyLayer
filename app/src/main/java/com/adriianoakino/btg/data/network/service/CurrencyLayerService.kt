package com.adriianoakino.btg.data.network.service

import com.adriianoakino.btg.data.models.ConverterModel
import com.adriianoakino.btg.data.models.CurrencyLayerModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyLayerService {

    @GET("list")
    fun getAllExchangesRate(@Query("access_key") key: String): Call<CurrencyLayerModel>

    @GET("live")
    fun getConverter(@Query("access_key") key: String, @Query("currencies") currency: String, @Query("source") source: String, @Query("format") format: String): Call<ConverterModel>
}
