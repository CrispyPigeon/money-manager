package com.ds.money_manager.extensions

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDateTime.toFormattedString(): String {
    val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
    return dtf.format(this)
}

fun LocalDateTime.toApiString(): String {
    val dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
    return this.atOffset(ZoneOffset.UTC).format(dtf)
}


