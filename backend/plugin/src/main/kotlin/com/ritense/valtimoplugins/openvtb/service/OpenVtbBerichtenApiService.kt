package com.ritense.valtimoplugins.openvtb.service

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.cfg.CoercionAction
import com.fasterxml.jackson.databind.cfg.CoercionInputShape
import com.fasterxml.jackson.databind.type.LogicalType
import com.ritense.valtimoplugins.openvtb.client.apis.BerichtenApi
import com.ritense.valtimoplugins.openvtb.client.models.Bericht
import com.ritense.valtimoplugins.openvtb.client.models.PaginatedBerichtList
import com.ritense.valtimoplugins.openvtb.client.models.PatchedBerichtGeopendOp
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestClient
import java.net.URI
import java.time.OffsetDateTime
import java.util.UUID

/**
 * Service for the Open VTB Berichten API (see `open-vtb-berichten-api.yaml`).
 *
 * The transport layer is the OpenAPI-generated [BerichtenApi]. A [BerichtenApi] instance
 * is bound to a single `baseUrl` and token, so one is built per call from the plugin's
 * configuration. The token is sent as `Authorization: Token <token>` as described by the
 * `tokenAuth` security scheme.
 */
class OpenVtbBerichtenApiService {
    fun getBerichten(
        baseUrl: URI,
        token: String,
        page: Int? = null,
        pageSize: Int? = null,
    ): PaginatedBerichtList = api(baseUrl, token).berichtenList(page, pageSize)

    fun getBericht(
        baseUrl: URI,
        token: String,
        uuid: UUID,
    ): Bericht = api(baseUrl, token).berichtenRetrieve(uuid)

    fun createBericht(
        baseUrl: URI,
        token: String,
        bericht: Bericht,
    ): Bericht = api(baseUrl, token).berichtenCreate(bericht)

    fun markGeopend(
        baseUrl: URI,
        token: String,
        uuid: UUID,
        geopendOp: OffsetDateTime,
    ): Bericht = api(baseUrl, token).berichtenPartialUpdate(uuid, PatchedBerichtGeopendOp(geopendOp))

    private fun api(
        baseUrl: URI,
        token: String,
    ): BerichtenApi =
        BerichtenApi(
            RestClient
                .builder()
                .baseUrl(baseUrl.toString())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Token $token")
                .messageConverters { converters ->
                    converters.removeIf { it is MappingJackson2HttpMessageConverter }
                    converters.add(MappingJackson2HttpMessageConverter(objectMapper))
                }.requestInterceptor { request, body, execution ->
                    logger.debug { "${request.method} ${request.uri} body=${String(body)}" }
                    execution.execute(request, body)
                }.build(),
        )

    companion object {
        private val logger = KotlinLogging.logger {}

        /**
         * ObjectMapper for the generated client:
         * - serializes `java.time` types (e.g. [OffsetDateTime]) as ISO-8601 strings instead of
         *   numeric timestamps, as required by the Open VTB Berichten API;
         * - omits null fields so read-only fields (`url`, `urn`, `uuid`) and unset optional fields
         *   are not sent in create/update request bodies;
         * - coerces an empty string to `null` for enums, because the API represents an unset
         *   `handelingsPerspectief` as `""` rather than omitting it or sending `null`.
         *
         * [Jackson2ObjectMapperBuilder] auto-registers the well-known modules (Kotlin, JSR-310)
         * so response deserialization keeps working.
         */
        private val objectMapper: ObjectMapper =
            Jackson2ObjectMapperBuilder
                .json()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build<ObjectMapper>()
                .apply {
                    coercionConfigFor(LogicalType.Enum)
                        .setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull)
                }
    }
}
