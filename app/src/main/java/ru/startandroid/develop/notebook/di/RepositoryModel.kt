package ru.startandroid.develop.notebook.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.startandroid.develop.notebook.data.repository.NoteRepositoryImpl
import ru.startandroid.develop.notebook.domain.NoteRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModel {

    @Binds
    fun provideNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository
}