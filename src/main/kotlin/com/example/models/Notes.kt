package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Notes(
    val notes: MutableList<Note>
)
