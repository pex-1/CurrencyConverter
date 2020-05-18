package practice.conversionapp.api

import practice.conversionapp.data.model.CurrencyResponse
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    suspend fun getRates(): List<CurrencyResponse>

}