package ru.startandroid.develop.notebook.screens.global.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.startandroid.develop.notebook.R

sealed class NoteUi(val id: Int)

@Parcelize
data class NoteUiModel(
    val noteId: Int?,
    val header: String,
    val description: String,
    val timeStamp: Long?,
    val createdDateFormatted: String?,
    val viewType: Int = R.layout.note_item
) : NoteUi(noteId ?: 0), Parcelable

data class NoteSearchHeaderModel(val viewType: Int = R.layout.note_search_item, val query: String = "") : NoteUi(viewType)