package com.example


import io.ktor.features.*
import java.beans.XMLDecoder

public abstract class CustomXmlConverter(private val xml: XMLDecoder) : ContentConverter {

}