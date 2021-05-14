package com.joram

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.joram.examples.ProtoModels.WhoIsRequest
import com.joram.examples.ProtoModels.WhoIsResponse
import io.dapr.client.DaprClientBuilder
import io.dapr.client.domain.HttpExtension

object InvokeGrpcClient {

    /**
     * Identifier in Dapr for the service this client will invoke.
     */
    private const val APP_A_APP_ID = "app-a"

    fun invokeServiceA(whoIsRequest: WhoIsRequest): Either<DomainError, WhoIsResponse> {
        val bytes: ByteArray? = DaprClientBuilder().build().use { client ->
            client.invokeMethod(APP_A_APP_ID, "whoIs", whoIsRequest.toByteArray(), HttpExtension.NONE, emptyMap()).block()
        }
        val whoIsResponse: WhoIsResponse? = WhoIsResponse.parseFrom(bytes)
        return whoIsResponse?.right() ?: DomainError("Something went wrong, there is no WhoIsResponse").left()
    }
}
