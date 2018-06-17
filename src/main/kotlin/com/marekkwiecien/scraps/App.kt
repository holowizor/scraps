package com.marekkwiecien.scraps

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authenticate
import io.ktor.auth.basic
import io.ktor.content.resources
import io.ktor.content.static
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.respondTemplate
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.routing

val service = ScrapsService()

fun Application.main() {
    install(DefaultHeaders)
    install(Compression)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            registerModule(JavaTimeModule())
        }
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(Application::class.java.classLoader, "templates")
    }
    install(Authentication) {
        basic {
            realm = "ktor"
            validate { credentials ->
                if (credentials.password == "test1234") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
    routing {
        get("/context") {
            call.respond(service.findAllContexts())
        }
        post("/context") {
            val context = call.receive<Context>()
            if (context.id == 0L) {
                val newContext = service.createNewContext(context.name)
                call.respond(newContext)
            } else {
                val updatedContext = service.saveContext(context)
                call.respond(updatedContext)
            }
        }
        put("/context") {
            val context = call.receive<Context>()
            if (context.id == 0L) {
                val newContext = service.createNewContext(context.name)
                call.respond(newContext)
            } else {
                val updatedContext = service.saveContext(context)
                call.respond(updatedContext)
            }
        }
        get("/context/{contextId}/scrap") {
            call.respond(service.findScrapsForContext(call.parameters["contextId"]!!.toLong()))
        }
        post("/context/{contextId}/scrap") {
            val scrap = call.receive<Scrap>()
            val updatedScrap = service.saveScrap(scrap)
            call.respond(updatedScrap)
        }
        put("/context/{contextId}/scrap") {
            val scrap = call.receive<Scrap>()
            val updatedScrap = service.saveScrap(scrap)
            call.respond(updatedScrap)
        }
        authenticate {
            get("/") {
                val ctx = Context(1L, "test")
                call.respondTemplate("index.ftl", mapOf("context" to ctx), "e")
            }
        }
        static("static") {
            resources("css")
            resources("js")
            resources("img")
        }
    }
}