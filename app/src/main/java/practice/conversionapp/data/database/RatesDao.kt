package practice.conversionapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import practice.conversionapp.data.model.CurrencyResponse

@Dao
interface RatesDao {

    @Query("SELECT * FROM rates_table")
    fun getRates(): List<CurrencyResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rates: List<CurrencyResponse>) : List<Long>

}