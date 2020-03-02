package practice.conversionapp.di.converter

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import practice.conversionapp.di.ViewModelKey
import practice.conversionapp.ui.converter.ConverterViewModel

@Module
abstract class ConverterViewModelsModule{

    @Binds
    @IntoMap
    @ViewModelKey(ConverterViewModel::class)
    abstract fun bindConverterViewModel(viewModel: ConverterViewModel): ViewModel
}