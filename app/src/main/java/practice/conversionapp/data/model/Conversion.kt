package practice.conversionapp.data.model

data class Conversion(
        var from: CurrencyResponse? = null,
        var to: CurrencyResponse? = null,
        var fromPosition: Int = 0,
        var toPosition: Int = 0,
        var result: Float = 0f)