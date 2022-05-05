package ru.startandroid.develop.notebook.screens.global.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteUiModel(
    val id: Int,
    val header: String,
    val description: String,
    val timeStamp: Long,
    val createdDateFormatted: String,
): Parcelable