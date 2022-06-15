package ru.startandroid.develop.notebook.screens.global.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.startandroid.develop.notebook.R
import java.util.*

@Parcelize
data class NoteUi(
    val id: Int?,
    val header: String,
    val description: String,
    val createdDate: Date,
    val creationDay: Int,
    var formattedTime: String,
) : Parcelable