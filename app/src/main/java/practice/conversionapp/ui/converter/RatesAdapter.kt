package practice.conversionapp.ui.converter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_rates.view.*
import practice.conversionapp.R
import practice.conversionapp.data.model.CurrencyResponse
import javax.inject.Inject

class RatesAdapter @Inject constructor() : RecyclerView.Adapter<RatesAdapter.RatesViewHolder>() {

    private var rates = listOf<CurrencyResponse>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        return RatesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rates, parent, false))
    }

    override fun getItemCount(): Int = rates.size

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        val rate = rates[position]

        with(holder.itemView) {
            currencyCodeTextView.text = rate.currencyCode
            currencyBuyingTextView.text = rate.buyingRate.toString()
            currencySellingTextView.text = rate.sellingRate.toString()
        }
    }

    fun setData(newRates: List<CurrencyResponse>){
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(RateItemDiffCallback(rates, newRates))
        rates = newRates
        diffResult.dispatchUpdatesTo(this)
    }

    class RateItemDiffCallback(private val oldRatesList: List<CurrencyResponse>, private val newRatesList: List<CurrencyResponse>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldRatesList[oldItemPosition].currencyCode == newRatesList[newItemPosition].currencyCode)
        }

        override fun getOldListSize(): Int = oldRatesList.size

        override fun getNewListSize(): Int = newRatesList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldRatesList[oldItemPosition] == newRatesList[newItemPosition]
        }

    }

    inner class RatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}