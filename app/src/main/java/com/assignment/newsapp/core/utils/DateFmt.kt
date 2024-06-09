package com.assignment.newsapp.core.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateFmt {
    companion object {
        const val yyyyMMdd =
            "yyyy-MM-dd"

        fun format(
            date: LocalDateTime,
            format: String
        ): String {
            val formatter =
                DateTimeFormatter.ofPattern(
                    format
                )
            return formatter.format(date)
        }

        fun parseIso(isoDate: String): LocalDateTime {
            return LocalDateTime.from(
                DateTimeFormatter.ISO_DATE_TIME.parse(
                    isoDate
                )
            )
        }
    }
}