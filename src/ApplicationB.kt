package com.joram

import arrow.core.Either
import arrow.core.right
import com.joram.examples.ProtoModels
import com.joram.model.CommunicationMethod
import com.joram.model.WhoIsRequest
import com.joram.model.WhoIsResponse
import com.joram.model.toResponse
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.moduleB(testing: Boolean = false) {

    routing {
        get("/") {
            Either.resolve(
                f = {
                    val domainLogic: Either<DomainError, String> = "HELLO WORLD!".right()
                    domainLogic
                },
                success = { a -> handleSuccessTextPlain(call, a) },
                error = { e -> handleDomainError(call, ::logError, e) },
                throwable = { throwable -> handleSystemFailure(call, ::logError, throwable) },
                unrecoverableState = { e -> logError(e) }
            )
        }

        get("/json/gson") {
            Either.resolve(
                f = {
                    val domainLogic: Either<DomainError, Map<String, String>> = mapOf("hello" to "world").right()
                    domainLogic
                },
                success = { a -> handleSuccess(call, a) },
                error = { e -> handleDomainError(call, ::logError, e) },
                throwable = { throwable -> handleSystemFailure(call, ::logError, throwable) },
                unrecoverableState = { e -> logError(e) }
            )
        }

        get("/who") {
            Either.resolve(
                f = { InvokeClient.invokeServiceA(WhoIsRequest(APP_NAME)) },
                success = { a -> handleSuccess(call, a) },
                error = { e -> handleDomainError(call, ::logError, e) },
                throwable = { throwable -> handleSystemFailure(call, ::logError, throwable) },
                unrecoverableState = { e -> logError(e) }
            )
        }

        get("/who2") {
            Either.resolve(
                f = {
                    val whoIsRequest = ProtoModels.WhoIsRequest.newBuilder().setRequestFrom(APP_NAME).build()
                    InvokeGrpcClient.invokeServiceA(whoIsRequest)
                },
                success = { whoIsResponse: ProtoModels.WhoIsResponse ->
                    handleSuccess(call, WhoIsResponse(whoIsResponse.requestFrom, whoIsResponse.responseFrom, whoIsResponse.communicationMethod, whoIsResponse.message))
                },
                error = { e -> handleDomainError(call, ::logError, e) },
                throwable = { throwable -> handleSystemFailure(call, ::logError, throwable) },
                unrecoverableState = { e -> logError(e) }
            )
        }

        post("/who") {
            Either.resolve(
                f = {
                    val whoIsRequest = call.receive<WhoIsRequest>()
                    whoIsRequest.toResponse(APP_NAME, CommunicationMethod.HTTP.name).right()
                },
                success = { a -> handleSuccess(call, a) },
                error = { e -> handleDomainError(call, ::logError, e) },
                throwable = { throwable -> handleSystemFailure(call, ::logError, throwable) },
                unrecoverableState = { e -> logError(e) }
            )
        }
    }
}

private const val APP_NAME = "app-b"
