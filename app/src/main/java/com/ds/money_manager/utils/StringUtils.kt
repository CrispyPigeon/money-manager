package com.ds.money_manager.utils

import com.ds.money_manager.Constants
import com.ds.money_manager.Constants.DATE_FORMAT
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object StringUtils {
    fun String.toDateString(): String {
        val date = LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        return date.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
    }
}