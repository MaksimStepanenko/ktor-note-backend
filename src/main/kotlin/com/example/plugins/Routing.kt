package com.example.plugins

import com.example.models.Note
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


fun Application.configureRouting() {

    val notes = mutableListOf<Note>()
    var counter = 0

    routing {
        route("/notes") {
            get {
                call.respond(notes)
            }
            post {
                val newNote = call.receive<Note>()
                notes.add(Note(id = ++counter, value = newNote.value))
                return@post call.respond(Note(id = counter, value = newNote.value))
            }

            route("/{id}") {
                get {
                    val id = call.parameters["id"]?.toInt()
                    val note = notes.find { it.id == id }
                    if (note != null) {
                        return@get call.respond(note)
                    } else {
                        throw NotFoundException("Not found note with id = $id")
                    }
                }
                put {
                    val id = call.parameters["id"]?.toInt()
                    val newNote = call.receive<Note>()
                    val note = notes.find { it.id == id }
                    if (note != null) {
                        note.value = newNote.value
                        return@put call.respond(note)
                    } else {
                        throw NotFoundException("Not found note with id = $id")
                    }
                }
                delete {
                    val id = call.parameters["id"]?.toInt()
                    val removeResultTrue = notes.removeIf { it.id == id }
                    if (removeResultTrue) {
                        call.respond(HttpStatusCode.OK, "")
                    } else {
                        throw NotFoundException("Not found note with id = $id")
                    }
                }

            }
        }

    }
}
