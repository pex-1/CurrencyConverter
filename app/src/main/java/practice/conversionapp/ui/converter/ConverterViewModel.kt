package practice.conversionapp.ui.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import practice.conversionapp.data.Repository
import practice.conversionapp.data.model.Conversion
import practice.conversionapp.data.model.CurrencyResponse
import practice.conversionapp.data.model.NetworkResponse
import javax.inject.Inject

class ConverterViewModel @Inject constructor(private val repository: Repository): ViewModel(){

    private lateinit var disposable: Disposable

    var conversion: Conversion = Conversion()

    private val _rates: MutableLiveData<List<CurrencyResponse>> = MutableLiveData()

    val rates: LiveData<List<CurrencyResponse>>
        get() = _rates

    private val _errorHandling: MutableLiveData<NetworkResponse> = MutableLiveData()

    val errorHandling: LiveData<NetworkResponse>
        get() = _errorHandling

    init {
        getRates()
    }

    fun getRates(){
        disposable = repository.getRates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _rates.value = it
                    onRetrieveListSuccess()
                },
                {
                    onRetrieveListError(it)
                }
            )
    }


    private fun onRetrieveListError(it: Throwable) {
        _errorHandling.value = NetworkResponse.error(it.localizedMessage)
    }

    private fun onRetrieveListSuccess() {
        _errorHandling.value = NetworkResponse.success()
    }


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}