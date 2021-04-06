package com.example.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import kotlinx.serialization.Serializable

@Serializable
@JacksonXmlRootElement(localName = "item")
data class Note(
    @JacksonXmlProperty(isAttribute = false, localName = "id")
    val id: Int?,
    @JacksonXmlProperty(isAttribute = false, localName = "value")
    var value: String
)
