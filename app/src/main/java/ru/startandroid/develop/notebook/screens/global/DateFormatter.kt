package ru.startandroid.develop.notebook.screens.global

import java.util.*

class DateFormatter {

    private val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
    private val sdfMain = SimpleDateFormat(MAIN_DATE_FORMAT)
    private val sdfOld = SimpleDateFormat(OLDER_DATE_FORMAT)

    fun formatDate(dateObject: DateTest): DateTest = when {
        dateObject.dayOfYear == currentDay -> formatDay(dateObject, FormatTypes.TODAY, sdfMain)
        (dateObject.dayOfYear + SINGLE_DAY) == currentDay -> formatDay(dateObject, FormatTypes.YESTERDAY, sdfMain)
        dateObject.dayOfYear == MAX_DAY_LIMIT && currentDay == SINGLE_DAY -> {
            formatDay(dateObject, FormatTypes.YESTERDAY, sdfMain)
        }
        else -> formatDay(dateObject, FormatTypes.OLDER_DATE, sdfOld)
    }

    private fun formatDay(dateObject: DateTest, dateType: FormatTypes, sdf: SimpleDateFormat): DateTest {
        val date = Date(dateObject.millis)
        val formattedDate = sdf.format(date)
        dateObject.formattedDate = when (dateType) {
            FormatTypes.TODAY -> "Today $formattedDate" // менять приставку на ui
            FormatTypes.YESTERDAY -> "Yesterday $formattedDate"
            FormatTypes.OLDER_DATE -> formattedDate
        }
        return dateObject
    }

    enum class FormatTypes {
        TODAY,
        YESTERDAY,
        OLDER_DATE
    }

    private companion object {
        const val MAIN_DATE_FORMAT = "HH:mm"
        const val OLDER_DATE_FORMAT = "MMMMM d"

        const val SINGLE_DAY = 1
        const val MAX_DAY_LIMIT = 365
    }
}

//data class DateTest(
//    val millis: Long,
//    val dayOfYear: Int,
//    var formattedDate: String
//)