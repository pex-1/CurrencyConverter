package practice.conversionapp.di.converter

import dagger.Module
import dagger.Provides
import practice.conversionapp.ui.converter.RatesAdapter

@Module
class ConverterModule {

    @Provides
    fun provideAdapter(): RatesAdapter{
        return RatesAdapter()
    }
}