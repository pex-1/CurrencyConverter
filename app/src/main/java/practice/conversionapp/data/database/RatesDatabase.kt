package practice.conversionapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import practice.conversionapp.data.model.CurrencyResponse

@Database(entities = [CurrencyResponse::class], version = 1, exportSchema = false)
abstract class RatesDatabase : RoomDatabase() {

    abstract fun ratesDao(): RatesDao

}
