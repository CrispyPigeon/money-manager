package com.ds.money_manager.extensions

import com.ds.money_manager.Constants.DATE_FORMAT
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateExtension {
    fun LocalDate.toDateString() = this.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
}