package ru.startandroid.develop.notebook.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.startandroid.develop.notebook.domain.NoteDateFormatter
import ru.startandroid.develop.notebook.domain.NoteRepository
import ru.startandroid.develop.notebook.screens.addedit.domain.AddEditInteractor
import ru.startandroid.develop.notebook.screens.addedit.domain.AddEditInteractorImpl
import ru.startandroid.develop.notebook.screens.main.domain.NoteMainInteractor
import ru.startandroid.develop.notebook.screens.main.domain.NoteMainInteractorImpl

@Module
@InstallIn(SingletonComponent::class)
class InteractorModule {

    @Provides
    fun provideNoteMainInteractor(noteRepository: NoteRepository, noteDateFormatter: NoteDateFormatter): NoteMainInteractor =
        NoteMainInteractorImpl(noteRepository, noteDateFormatter)


    @Provides
    fun provideAddEditInteractor(noteRepository: NoteRepository): AddEditInteractor =
        AddEditInteractorImpl(noteRepository)
}