package practice.conversionapp.api

import io.reactivex.Flowable
import practice.conversionapp.data.model.CurrencyResponse
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    fun getRates(): Flowable<List<CurrencyResponse>>
}