package com.assignment.newsapp.core.utils.serializer

import com.assignment.newsapp.core.utils.formatter.DateFmt
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeSerializer :
    KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "LocalDateTime",
            PrimitiveKind.STRING
        )

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return DateFmt.parseIso(decoder.decodeString())
    }

    override fun serialize(
        encoder: Encoder,
        value: LocalDateTime
    ) {
        val dateStr =
            DateTimeFormatter.ISO_DATE_TIME.format(
                value
            )
        encoder.encodeString(dateStr)
    }
}