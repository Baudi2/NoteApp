package ru.startandroid.develop.notebook.screens.global.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.startandroid.develop.notebook.R

@Parcelize
data class NoteUi(
    val noteId: Int?,
    val header: String,
    val description: String,
    val timeStamp: Long?,
    val createdDateFormatted: String?,
    val viewType: Int = R.layout.note_item
) : Parcelable