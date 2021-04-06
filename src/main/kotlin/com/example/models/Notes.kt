package com.example.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import kotlinx.serialization.Serializable

@Serializable
@JacksonXmlRootElement(localName = "ArrayList")
data class Notes (
    @JacksonXmlElementWrapper(localName = "attributes")
    val attributes: List<Note>
    )