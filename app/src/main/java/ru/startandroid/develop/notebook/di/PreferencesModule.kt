package ru.startandroid.develop.notebook.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.startandroid.develop.notebook.sharedpreferences.PreferenceHelper
import ru.startandroid.develop.notebook.sharedpreferences.PreferenceHelperImpl

@Module
@InstallIn(SingletonComponent::class)
interface PreferencesModule {

    @Binds
    abstract fun providePreferenceHelper(preferenceHelperImpl: PreferenceHelperImpl): PreferenceHelper
}