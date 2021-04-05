package com.example


import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import nl.adaptivity.xmlutil.serialization.XML
import kotlin.reflect.jvm.jvmErasure


public class CustomXmlConverter(private val xml: XML = XML()) : ContentConverter {
    override suspend fun convertForSend(
        context: PipelineContext<Any, ApplicationCall>,
        contentType: ContentType,
        value: Any
    ): Any {
        return TextContent(
            XmlMapper().writeValueAsString(value),
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
            XmlMapper().readValue(reader, javaType.javaObjectType)
//            gson.fromJson(reader, javaType.javaObjectType) ?: throw UnsupportedNullValuesException()
        }
    }
}