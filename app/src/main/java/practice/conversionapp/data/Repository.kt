package practice.conversionapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import practice.conversionapp.api.ApiService
import practice.conversionapp.data.database.RatesDao
import practice.conversionapp.data.model.CurrencyResponse


class Repository(private val apiService: ApiService, private val ratesDao: RatesDao) {

    private val rates = MutableLiveData<List<CurrencyResponse>>()
    fun ratesResponse(): LiveData<List<CurrencyResponse>> = rates

    private fun initDatabase(rates: List<CurrencyResponse>) {
        val keys = ratesDao.insert(rates)
    }

    suspend fun getRates() {
        try {
            withContext(IO) {
                val temp = apiService.getRates()
                rates.postValue(temp)
                initDatabase(temp)
            }
        } catch (error: Throwable) {
            withContext(IO) {
                val data = ratesDao.getRates()
                rates.postValue(data)
                if (data.isEmpty()) throw GetRatesError("Unable to get rates", error)
            }

        }
    }


    class GetRatesError(message: String, cause: Throwable) : Throwable(message, cause)


}