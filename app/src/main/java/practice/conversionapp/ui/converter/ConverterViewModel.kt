package practice.conversionapp.ui.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import practice.conversionapp.data.Repository
import practice.conversionapp.data.model.Conversion
import practice.conversionapp.data.model.CurrencyResponse

class ConverterViewModel(private val repository: Repository) : ViewModel() {

    var conversion: Conversion = Conversion()

    //spinner
    private val _spinner = MutableLiveData<Boolean>()

    val spinner: LiveData<Boolean>
        get() = _spinner


    //snackbar
    private val _snackBar = MutableLiveData<String?>()

    val snackbar: LiveData<String?>
        get() = _snackBar


    val rates: LiveData<List<CurrencyResponse>>

    init {
        getRates()
        rates = repository.ratesResponse()
    }

    fun getRates() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                repository.getRates()
                _spinner.value = true
            } catch (error: Repository.GetRatesError) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }

        }
    }

}