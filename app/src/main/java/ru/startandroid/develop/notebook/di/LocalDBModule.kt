package ru.startandroid.develop.notebook.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.startandroid.develop.notebook.model.NoteDatabase
import ru.startandroid.develop.notebook.sharedpreferences.PreferenceHelperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDBModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, NoteDatabase::class.java, NOTE_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideNoteDao(db: NoteDatabase) = db.noteDao()

    @Provides
    @Singleton
    fun providePreferenceHelperImpl(app: Application) = PreferenceHelperImpl(app)

    private const val NOTE_DATABASE_NAME = "note_database"
}