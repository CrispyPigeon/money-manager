package com.ds.money_manager.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDate.toFormattedString(): String {
    val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
    return dtf.format(this)
}

fun LocalDate.toApiString(): String {
    return "${this.year}-${this.monthValue}-${this.dayOfMonth}T00:00:00.396z"
}