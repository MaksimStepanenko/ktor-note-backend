package com.example.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    @JacksonXmlProperty(isAttribute = true) val id: Int = 0,
    @JacksonXmlProperty(isAttribute = true) var value: String
)
