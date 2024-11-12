package com.ds.money_manager.extensions

import com.ds.money_manager.Constants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toDateString(): String {
    val date = LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    return date.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
}

fun String.toLocalDateTime(): LocalDateTime {
   return LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}