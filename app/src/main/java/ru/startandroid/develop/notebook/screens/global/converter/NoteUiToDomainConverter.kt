package ru.startandroid.develop.notebook.screens.global.converter

import ru.startandroid.develop.notebook.domain.model.NoteDomainModel
import ru.startandroid.develop.notebook.screens.global.model.NoteUiModel

fun NoteUiModel.toDomain() = NoteDomainModel(
    id = id,
    header = header,
    description = description,
    timeStamp = timeStamp,
    createdDateFormatted = createdDateFormatted,
)