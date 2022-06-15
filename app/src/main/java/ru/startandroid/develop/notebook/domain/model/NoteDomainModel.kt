package ru.startandroid.develop.notebook.domain.model

import java.util.*

data class NoteDomainModel(
    val id: Int?,
    val header: String,
    val description: String,
    val createdDate: Date,
    val creationDay: Int,
    var formattedTime: String,
)