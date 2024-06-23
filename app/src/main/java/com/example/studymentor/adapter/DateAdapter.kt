package com.example.studymentor.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.text.SimpleDateFormat
import java.util.*
class DateAdapter : TypeAdapter<Date>() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    override fun write(out: JsonWriter, value: Date) {
        out.value(dateFormat.format(value))
    }

    override fun read(input: JsonReader): Date {
        return dateFormat.parse(input.nextString())!!
    }
}