package practice.conversionapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import practice.conversionapp.di.converter.ConverterModule
import practice.conversionapp.di.converter.ConverterViewModelsModule
import practice.conversionapp.ui.converter.ConverterActivity

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [ConverterViewModelsModule::class, ConverterModule::class])
    abstract fun contributeConverterActivity(): ConverterActivity
}