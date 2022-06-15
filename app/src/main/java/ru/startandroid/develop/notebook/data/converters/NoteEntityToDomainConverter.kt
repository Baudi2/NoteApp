package ru.startandroid.develop.notebook.data.converters

import ru.startandroid.develop.notebook.data.enitities.NoteEntity
import ru.startandroid.develop.notebook.domain.model.NoteDomainModel

fun NoteEntity.toDomain() = NoteDomainModel(
    id = id,
    header = header,
    description = description,
    createdDate = createdDate,
    creationDay = creationDay,
    formattedTime = formattedDay
)