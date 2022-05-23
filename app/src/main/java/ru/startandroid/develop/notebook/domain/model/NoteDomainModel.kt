package ru.startandroid.develop.notebook.domain.model

data class NoteDomainModel(
    val id: Int?,
    val header: String,
    val description: String,
    val timeStamp: Long?,
    val createdDateFormatted: String?,
)