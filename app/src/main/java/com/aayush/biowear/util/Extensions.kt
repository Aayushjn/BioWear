package com.aayush.biowear.util

import android.os.Parcel
import java.util.Date

inline fun <T> Parcel.readNullable(reader: () -> T) =
    if (readInt() != 0) reader() else null

inline fun <T> Parcel.writeNullable(value: T?, writer: (T) -> Unit) {
    if (value != null) {
        writeInt(1)
        writer(value)
    } else {
        writeInt(0)
    }
}

fun Parcel.readDate() = readNullable { Date(readLong()) }

fun Parcel.writeDate(value: Date?) = writeNullable(value) { writeLong(it.time) }

inline fun <reified T : Enum<T>> Parcel.readEnum() = readInt().let { if (it >= 0) enumValues<T>()[it] else null }

fun <T : Enum<T>> Parcel.writeEnum(value: T?) = writeInt(value?.ordinal ?: -1)