package practice.conversionapp.data

import io.reactivex.Flowable
import practice.conversionapp.api.ApiService
import practice.conversionapp.data.model.CurrencyResponse
import javax.inject.Inject


class Repository @Inject constructor(private val apiService: ApiService) {

    fun getRates(): Flowable<List<CurrencyResponse>>{
        return apiService.getRates()
    }
}