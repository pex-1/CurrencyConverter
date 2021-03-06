package practice.conversionapp.data.model


import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("buying_rate")
    val buyingRate: Float,
    @SerializedName("currency_code")
    val currencyCode: String,
    @SerializedName("median_rate")
    val medianRate: Float,
    @SerializedName("selling_rate")
    val sellingRate: Float,
    @SerializedName("unit_value")
    val unitValue: Int
){
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass) return false

        other as CurrencyResponse

        if (buyingRate != other.buyingRate) return false
        if (currencyCode != other.currencyCode) return false
        if (medianRate != other.medianRate) return false
        if (sellingRate != other.sellingRate) return false
        if (unitValue != other.unitValue) return false

        return true
    }
}