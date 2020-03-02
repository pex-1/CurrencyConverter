package practice.conversionapp.ui.converter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_currency.*
import practice.conversionapp.Constants
import practice.conversionapp.R
import practice.conversionapp.data.model.CurrencyResponse
import practice.conversionapp.data.model.NetworkResponse
import practice.conversionapp.ui.viewmodels.ViewModelProviderFactory
import practice.conversionapp.util.*
import javax.inject.Inject


class ConverterActivity : DaggerAppCompatActivity(), AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var viewModel: ConverterViewModel

    @Inject
    lateinit var ratesAdapter: RatesAdapter

    var rates : List<CurrencyResponse>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        initSpinners()

        viewModel = ViewModelProvider(this, providerFactory).get(ConverterViewModel::class.java)

        if(viewModel.conversion.result != 0f){
            medianTextView.text = viewModel.conversion.result.toString()
        }
        subscribeObservers()

        manageButtonClicks()

    }

    private fun manageButtonClicks() {
        convertButton.setOnClickListener {
            if(rates != null){
                hideKeyboard()
                calculateResult()
            }else if(applicationContext.isConnectedToNetwork()){
                viewModel.getRates()
            }
            else{
                hideKeyboard()
                window.decorView.rootView.snackbar(Constants.NO_NETWORK_CONNECTION)
            }
        }

        switchButton.setOnClickListener {
            swap()
        }
    }


    private fun swap(){
        val fromPos = viewModel.conversion.fromPosition
        val fromResponse = viewModel.conversion.from
        with(viewModel.conversion){
            from = to
            fromPosition = toPosition
            to = fromResponse
            toPosition = fromPos
            fromSpinner.setSelection(fromPosition)
            toSpinner.setSelection(toPosition)
        }

    }
    private fun calculateResult() {
        if (!amountEditText.text.matches(Regex(Constants.REGEX_PATTERN))) {
            window.decorView.rootView.snackbar(Constants.WRONG_INPUT_MESSAGE)
        } else {
            viewModel.conversion.from = rates?.get(viewModel.conversion.fromPosition)
            viewModel.conversion.to = rates?.get(viewModel.conversion.toPosition)

            viewModel.conversion.result = ConversionUtils.convert(viewModel.conversion, amountEditText.text.toString().toFloat())
            medianTextView.text = viewModel.conversion.result.toString()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(convertButton.windowToken, 0)
    }

    private fun subscribeObservers() {
        ratesProgressBar.show()
        viewModel.rates.observe(this, Observer {
            if (it != null) {
                initRecycler(it)
                rates = it
            }
        })

        viewModel.errorHandling.observe(this, Observer {
            if(it.status == NetworkResponse.NetworkStatus.SUCCESS){
                ratesProgressBar.hide()
            }else{
                ratesProgressBar.hide()
                window.decorView.rootView.snackbar("Error: ${it.message}")
            }
        })
    }

    private fun initSpinners() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = adapter
        toSpinner.adapter = adapter
        fromSpinner.onItemSelectedListener = this
        toSpinner.onItemSelectedListener = this

    }

    private fun initRecycler(rates: List<CurrencyResponse>) {
        ratesRecyclerView.apply {
            ratesAdapter.setData(rates)
            layoutManager = LinearLayoutManager(context)
            adapter = ratesAdapter
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        when (parent?.id) {
            R.id.fromSpinner -> {
                viewModel.conversion.fromPosition = position
            }
            R.id.toSpinner -> {
                viewModel.conversion.toPosition = position
            }
        }
    }
}
