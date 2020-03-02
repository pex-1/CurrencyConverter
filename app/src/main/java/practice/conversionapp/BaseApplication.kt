package practice.conversionapp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import practice.conversionapp.di.DaggerAppComponent

class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}