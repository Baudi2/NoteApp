package ru.startandroid.develop.notebook.screens.global.converter

import ru.startandroid.develop.notebook.domain.model.NoteDomainModel
import ru.startandroid.develop.notebook.screens.global.model.NoteUi

fun NoteUi.toDomain() = NoteDomainModel(
    id = noteId,
    header = header,
    description = description,
    timeStamp = timeStamp,
    createdDateFormatted = createdDateFormatted,
)