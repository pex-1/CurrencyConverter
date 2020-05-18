package practice.conversionapp.ui.converter

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_currency.*
import org.koin.android.ext.android.inject
import practice.conversionapp.Constants
import practice.conversionapp.R
import practice.conversionapp.data.model.CurrencyResponse
import practice.conversionapp.util.*


class ConverterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    private val viewModel: ConverterViewModel by inject()

    private val ratesAdapter: RatesAdapter by inject()

    var rates : List<CurrencyResponse>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        initSpinners()

        //viewModel = ViewModelProvider(this).get(ConverterViewModel::class.java)

        if(viewModel.conversion.result != 0f){
            medianTextView.text = viewModel.conversion.result.toString()
        }
        subscribeObservers()

        manageButtonClicks()

    }

    private fun manageButtonClicks() {
        convertButton.setOnClickListener {
            when {
                rates != null -> {
                    hideKeyboard()
                    calculateResult()
                }
                applicationContext.isConnectedToNetwork() -> {
                    viewModel.getRates()
                }
                else -> {
                    hideKeyboard()
                    window.decorView.rootView.snackbar(Constants.NO_NETWORK_CONNECTION)
                }
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

        viewModel.spinner.observe(this, Observer {
            if (it){
                ratesProgressBar.show()
            }else{
                ratesProgressBar.hide()
            }
        })

        viewModel.snackbar.observe(this, Observer {
            if(!it.isNullOrBlank()){
                window.decorView.rootView.snackbar("$it")
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
