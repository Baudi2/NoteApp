package ru.startandroid.develop.notebook.domain

import ru.startandroid.develop.notebook.core.Constants.MAIN_DATE_FORMAT
import ru.startandroid.develop.notebook.core.Constants.OLDER_DATE_FORMAT
import ru.startandroid.develop.notebook.core.DateFormatTypes
import ru.startandroid.develop.notebook.domain.model.NoteDomainModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NoteDateFormatter @Inject constructor() {

    private val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
    private val sdfMain = SimpleDateFormat(MAIN_DATE_FORMAT, Locale.getDefault())
    private val sdfOld = SimpleDateFormat(OLDER_DATE_FORMAT, Locale.getDefault())

    fun formatDate(noteDomain: NoteDomainModel): NoteDomainModel = when {
        noteDomain.creationDay == currentDay -> {
            formatNoteDate(noteDomain, DateFormatTypes.TODAY, sdfMain)
        }
        (noteDomain.creationDay + SINGLE_DAY) == currentDay -> {
            formatNoteDate(noteDomain, DateFormatTypes.YESTERDAY, sdfMain)
        }
        noteDomain.creationDay == MAX_DAY_LIMIT && currentDay == SINGLE_DAY -> {
            formatNoteDate(noteDomain, DateFormatTypes.YESTERDAY, sdfMain)
        }
        else -> formatNoteDate(noteDomain, DateFormatTypes.OLDER_DATE, sdfOld)
    }

    private fun formatNoteDate(
        noteDomain: NoteDomainModel,
        dateType: DateFormatTypes,
        sdf: SimpleDateFormat
    ): NoteDomainModel {
        val formattedDate = sdf.format(noteDomain.createdDate)
        noteDomain.formattedTime = when (dateType) {
            DateFormatTypes.TODAY -> dateType.name + formattedDate
            DateFormatTypes.YESTERDAY -> dateType.name + formattedDate
            DateFormatTypes.OLDER_DATE -> formattedDate
        }
        return noteDomain
    }

    private companion object {

        const val SINGLE_DAY = 1
        const val MAX_DAY_LIMIT = 365
    }
}