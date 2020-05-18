package practice.conversionapp.util

import practice.conversionapp.data.model.Conversion

object ConversionUtils {

    fun convert(conversion: Conversion, amount: Float): Float{
        val base = (conversion.from!!.medianRate/conversion.to!!.medianRate)*amount
        return (base * conversion.to!!.unitValue)/conversion.from!!.unitValue
    }
}