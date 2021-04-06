package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class HttpBinError(
    val request: String,
    val message: String,
    val code: String,
)
