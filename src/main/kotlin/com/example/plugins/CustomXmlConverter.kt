package com.example.plugins


import com.example.models.Note
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.ktor.application.*
import io.ktor.content.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.util.pipeline.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import kotlin.reflect.jvm.jvmErasure


class CustomXmlConverter(private val xml: XML = XML {policy = JacksonPolicy}) : ContentConverter {
    override suspend fun convertForSend(
        context: PipelineContext<Any, ApplicationCall>,
        contentType: ContentType,
        value: Any
    ): Any {
        return TextContent(
            XmlMapper().writeValueAsString(value),
//            xml.encodeToString(value),
            contentType.withCharset(context.call.suitableCharset())
        )
    }

    override suspend fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
        val request = context.subject
        val channel = request.value as? ByteReadChannel ?: return null
        val type = request.typeInfo
        val javaType = type.jvmErasure

//        val gson: Gson = Gson()
//        if (gson.isExcluded(javaType)) {
//            throw ExcludedTypeGsonException(javaType)
//        }

        return withContext(Dispatchers.IO) {
            val reader = channel.toInputStream().reader(context.call.request.contentCharset() ?: Charsets.UTF_8)
//            xml.decodeFromString<Note>(reader.toString())
            XmlMapper().readValue(reader, Note::class.java)
//            gson.fromJson(reader, javaType.javaObjectType) ?: throw UnsupportedNullValuesException()
        }
    }
}