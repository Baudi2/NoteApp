package ru.startandroid.develop.notebook.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.startandroid.develop.notebook.data.sharedpreferences.PreferenceHelper
import ru.startandroid.develop.notebook.data.sharedpreferences.PreferenceHelperImpl

@Module
@InstallIn(SingletonComponent::class)
interface PreferencesModule {

    @Binds
    fun providePreferenceHelper(preferenceHelperImpl: PreferenceHelperImpl): PreferenceHelper
}