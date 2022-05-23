package ru.startandroid.develop.notebook.data.converters

import ru.startandroid.develop.notebook.data.enitities.NoteEntity
import ru.startandroid.develop.notebook.domain.model.NoteDomainModel

fun NoteDomainModel.toEntity(): NoteEntity =
    if (this.id == null) {
        NoteEntity(
            header = header,
            description = description
        )
    } else {
        NoteEntity(
            id = id,
            header = header,
            description = description,
            timeStamp = timeStamp ?: 0,
        )
    }