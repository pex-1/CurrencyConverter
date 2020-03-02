package practice.conversionapp.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import practice.conversionapp.ui.viewmodels.ViewModelProviderFactory

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory?): ViewModelProvider.Factory?
}