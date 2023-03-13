package com.example.justordinarydiaryapp.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeHelper {

    const val FORMAT_DAY_MONTHNAME_YEAR_HOUR24_MINUTE = "dd MMM yyyy, HH:mm"
    const val FORMAT_YEAR_MONTH_DAY_HOUR24_MINUTE_SECOND_MILLISECOND_TIMEZONE =
        "yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'"

    /**
     * Obtain a formatted String from another String.
     * @param sourceString String to parse
     * @param sourceFormat The date/time format
     * @param targetFormat The date/time format
     * @param targetLocale Requested [Locale]
     * @return the formatted String
     */
    fun getStringFromString(
        sourceString: String?,
        sourceFormat: String,
        targetFormat: String,
        targetLocale: Locale = Locale.getDefault()
    ): String? {
        return getStringFromString(
            sourceString,
            sourceFormat,
            Locale.getDefault(),
            targetFormat,
            targetLocale
        )
    }

    /**
     * Obtain a formatted String from another String.
     * @param sourceString String to parse
     * @param sourceFormat The date/time format
     * @param sourceLocale Requested [Locale]
     * @param targetFormat The date/time format
     * @param targetLocale Requested [Locale]
     * @return the formatted String
     */
    fun getStringFromString(
        sourceString: String?,
        sourceFormat: String,
        sourceLocale: Locale,
        targetFormat: String,
        targetLocale: Locale = Locale.getDefault()
    ): String? {
        val calendar = getCalendarFromString(sourceString, sourceFormat, sourceLocale)
        return getStringFromCalendar(calendar, targetFormat, targetLocale)
    }

    /**
     * Obtain a formatted String from a data.
     * @param calendar The [Calendar] object
     * @param format The date/time format
     * @param locale Requested [Locale]
     * @return the formatted String
     */
    fun getStringFromCalendar(
        calendar: Calendar?,
        format: String,
        locale: Locale = Locale.getDefault()
    ): String? {
        val date = getDateFromCalendar(calendar)
        return getStringFromDate(date, format, locale)
    }

    /**
     * Obtain a formatted String from a data.
     * @param date The [Date] object
     * @param format The date/time format
     * @param locale Requested [Locale]
     * @return the formatted String
     */
    fun getStringFromDate(
        date: Date?,
        format: String,
        locale: Locale = Locale.getDefault()
    ): String? {
        return try {
            if (date == null) throw NullPointerException("Date should not be null")
            getFormatter(format, locale).format(date)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Convert data to [Date].
     * @param string String to parse
     * @param format The date/time format
     * @param locale Requested [Locale]
     * @return the [Date] object
     */
    fun getDateFromString(
        string: String?,
        format: String,
        locale: Locale = Locale.getDefault()
    ): Date? {
        return try {
            if (string.isNullOrBlank()) throw NullPointerException("String should not be null or blank")
            return getFormatter(format, locale).parse(string)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Convert data to [Date].
     * @param calendar The [Calendar] object
     * @return the [Date] object
     */
    fun getDateFromCalendar(calendar: Calendar?): Date? {
        return calendar?.time
    }

    /**
     * Convert data to [Calendar].
     * @param string String to parse
     * @param format The date/time format
     * @param locale Requested [Locale]
     * @return the [Calendar] object
     */
    fun getCalendarFromString(
        string: String?,
        format: String,
        locale: Locale = Locale.getDefault()
    ): Calendar? {
        return try {
            getCalendarFromDate(getDateFromString(string, format, locale))
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Convert data to [Calendar].
     * @param date The [Date] object
     * @return the [Calendar] object
     */
    fun getCalendarFromDate(date: Date?): Calendar? {
        return if (date != null) Calendar.getInstance().apply {
            time = date
        } else {
            null
        }
    }

    /** Obtain a [SimpleDateFormat] for formatting */
    private fun getFormatter(format: String, locale: Locale): SimpleDateFormat {
        return SimpleDateFormat(format, locale)
    }
}