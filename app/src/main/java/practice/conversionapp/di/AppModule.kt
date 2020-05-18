package practice.conversionapp.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import practice.conversionapp.Constants
import practice.conversionapp.api.ApiService
import practice.conversionapp.data.database.RatesDatabase
import practice.conversionapp.data.Repository
import practice.conversionapp.ui.converter.ConverterViewModel
import practice.conversionapp.ui.converter.RatesAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single { Repository(get(), get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): RatesDatabase {
        return Room.databaseBuilder(application, RatesDatabase::class.java, "rates_database")
            .allowMainThreadQueries()
            .build()
    }

    factory { provideDatabase(androidApplication()) }
    factory { get<RatesDatabase>().ratesDao() }
}

val networkModule = module {


    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    single { provideGsonBuilder() }

    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    single { provideGsonConverterFactory(get()) }

    fun provideRetrofit(factory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(factory)
            .build()
    }
    single { provideRetrofit(get()) }

    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    single { provideApiService(get()) }

}

val converterModule = module {
    single { RatesAdapter() }
}

val viewModelsModule = module {
    single { ConverterViewModel(get()) }
}
