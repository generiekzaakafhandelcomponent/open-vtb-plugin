package com.ritense.valtimoplugins.openvtb.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.ritense.valtimo.contract.annotation.SkipComponentScan
import com.ritense.valtimoplugins.openvtb.domain.Bericht
import com.ritense.valtimoplugins.openvtb.domain.PaginatedBerichtList
import com.ritense.valtimoplugins.openvtb.domain.PatchedBerichtGeopendOp
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.UUID
import kotlin.jvm.java

/**
 * Client for the Berichten API (see `openapi.yaml`).
 *
 * Every operation takes the API `baseUrl` (e.g. `https://example.com/berichten/api/v1`)
 * and a `token`; the token is sent as `Authorization: Token <token>` as described by
 * the `tokenAuth` security scheme.
 */
@SkipComponentScan
@Component
class BerichtenApiClient(
    private val restClient: RestClient = defaultRestClient(),
) {
    /** `GET /berichten` — Vraag alle berichten aan. */
    fun berichtenList(
        baseUrl: URI,
        token: String,
        page: Int? = null,
        pageSize: Int? = null,
    ): PaginatedBerichtList =
        restClient
            .get()
            .uri(
                berichtenUri(baseUrl) {
                    page?.let { queryParam("page", it) }
                    pageSize?.let { queryParam("pageSize", it) }
                },
            ).header(HttpHeaders.AUTHORIZATION, "Token $token")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(PaginatedBerichtList::class.java)
            ?: error("Empty response body for GET /berichten")

    /** `POST /berichten` — Maak een bericht aan. */
    fun berichtenCreate(
        baseUrl: URI,
        token: String,
        bericht: Bericht,
    ): Bericht {
        logger.debug { "berichtenCreate bericht=$bericht" }

        val response =
            restClient
                .post()
                .uri(berichtenUri(baseUrl))
                .header(HttpHeaders.AUTHORIZATION, "Token $token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(bericht)
                .retrieve()
                .body(Bericht::class.java)
                ?: error("Empty response body for POST /berichten")

        logger.debug { "berichtenCreate response=$response" }

        return response
    }

    /** `GET /berichten/{uuid}` — Een specifiek bericht opvragen. */
    fun berichtenRetrieve(
        baseUrl: URI,
        token: String,
        uuid: UUID,
    ): Bericht =
        restClient
            .get()
            .uri(berichtenUri(baseUrl, uuid))
            .header(HttpHeaders.AUTHORIZATION, "Token $token")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(Bericht::class.java)
            ?: error("Empty response body for GET /berichten/$uuid")

    /** `PATCH /berichten/{uuid}` — Werk het veld 'geopendOp' van een bericht bij. */
    fun berichtenPartialUpdate(
        baseUrl: URI,
        token: String,
        uuid: UUID,
        patch: PatchedBerichtGeopendOp,
    ): Bericht =
        restClient
            .patch()
            .uri(berichtenUri(baseUrl, uuid))
            .header(HttpHeaders.AUTHORIZATION, "Token $token")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(patch)
            .retrieve()
            .body(Bericht::class.java)
            ?: error("Empty response body for PATCH /berichten/$uuid")

    private fun berichtenUri(
        baseUrl: URI,
        uuid: UUID? = null,
        customize: UriComponentsBuilder.() -> Unit = {},
    ): URI {
        val builder =
            UriComponentsBuilder
                .fromUri(baseUrl)
                .path("/berichten")
        if (uuid != null) {
            builder.pathSegment(uuid.toString())
        }
        builder.customize()
        return builder.build().toUri()
    }

    companion object {
        private val logger = KotlinLogging.logger {}

        /**
         * ObjectMapper that serializes `java.time` types (e.g. [java.time.OffsetDateTime]) as
         * ISO-8601 strings instead of numeric timestamps, as required by the Berichten API.
         * [org.springframework.http.converter.json.Jackson2ObjectMapperBuilder] auto-registers the well-known modules (Kotlin, JSR-310)
         * so response deserialization keeps working.
         */
        private val objectMapper: ObjectMapper =
            Jackson2ObjectMapperBuilder
                .json()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build()

        private fun defaultRestClient(): RestClient =
            RestClient
                .builder()
                .messageConverters { converters ->
                    converters.removeIf { it is MappingJackson2HttpMessageConverter }
                    converters.add(MappingJackson2HttpMessageConverter(objectMapper))
                }.requestInterceptor { request, body, execution ->
                    logger.debug { "${request.method} ${request.uri} body=${String(body)}" }
                    execution.execute(request, body)
                }.build()
    }
}
