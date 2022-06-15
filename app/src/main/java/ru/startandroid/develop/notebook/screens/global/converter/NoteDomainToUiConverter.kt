package ru.startandroid.develop.notebook.screens.global.converter

import ru.startandroid.develop.notebook.domain.model.NoteDomainModel
import ru.startandroid.develop.notebook.screens.global.model.NoteUi

fun NoteDomainModel.toUi() = NoteUi(
    id = id,
    header = header,
    description = description,
    createdDate = createdDate,
    creationDay = creationDay,
    formattedTime = formattedTime
)