package practice.conversionapp.di

import dagger.Module
import dagger.Provides
import practice.conversionapp.api.ApiService
import practice.conversionapp.data.Repository

@Module
class AppModule {

    @Provides
    fun provideRepository(apiService: ApiService): Repository{
        return Repository(apiService)
    }
}