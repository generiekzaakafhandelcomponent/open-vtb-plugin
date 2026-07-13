/*
 * Copyright 2026 Ritense BV, the Netherlands.
 *
 * Licensed under EUPL, Version 1.2 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ritense.valtimoplugins.openvtb.plugin.berichten

import com.ritense.plugin.annotation.Plugin
import com.ritense.plugin.annotation.PluginAction
import com.ritense.plugin.annotation.PluginActionProperty
import com.ritense.plugin.annotation.PluginProperty
import com.ritense.processlink.domain.ActivityTypeWithEventName.SERVICE_TASK_START
import com.ritense.valtimoplugins.openvtb.client.models.Bericht
import com.ritense.valtimoplugins.openvtb.client.models.Bijlage
import com.ritense.valtimoplugins.openvtb.client.models.HandelingsPerspectiefEnum
import com.ritense.valtimoplugins.openvtb.client.models.IsGerelateerdAan
import com.ritense.valtimoplugins.openvtb.service.BerichtenApiService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.operaton.bpm.engine.delegate.DelegateExecution
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestClientResponseException
import java.net.URI
import java.time.OffsetDateTime

/**
 * Plugin for the Berichten API. The [baseUrl] points at the API root
 * (e.g. `https://example.com/berichten/api/v1`) and [token] is the API token
 * sent as `Authorization: Token <token>`.
 */
@Plugin(
    key = "berichten",
    title = "Berichten Plugin",
    description = "Registreer en raadpleeg berichten via de Open VTB Berichten API.",
)
open class BerichtenApiPlugin(
    private val berichtenApiService: BerichtenApiService,
) {
    @PluginProperty(key = "baseUrl", secret = false)
    lateinit var baseUrl: String

    @PluginProperty(key = "token", secret = true)
    lateinit var token: String

    /**
     * Creates a Bericht and stores the URN of the created bericht in the process variable
     * named by [resultingVariable].
     *
     * If the API responds with a 4xx or 5xx status and [errorVariable] is set, the error
     * (status code and response body) is written to that process variable and the action
     * completes normally so the process can branch on it. When [errorVariable] is not set,
     * the failure propagates as an exception.
     */
    @PluginAction(
        key = "create-bericht",
        title = "Maak bericht aan",
        description = "Registreert een nieuw bericht via de Berichten API.",
        activityTypes = [SERVICE_TASK_START],
    )
    open fun createBericht(
        execution: DelegateExecution,
        @PluginActionProperty onderwerp: String,
        @PluginActionProperty isGerelateerdAan: List<IsGerelateerdAan>? = null,
        @PluginActionProperty handelingsPerspectief: String? = null,
        @PluginActionProperty einddatumHandelingsTermijn: OffsetDateTime? = null,
        @PluginActionProperty berichtTekst: String,
        @PluginActionProperty ontvanger: String,
        @PluginActionProperty mijnOverheidBerichtenbox: Boolean,
        @PluginActionProperty publicatiedatum: OffsetDateTime? = null,
        @PluginActionProperty referentie: String? = null,
        @PluginActionProperty geopendOp: OffsetDateTime? = null,
        @PluginActionProperty berichtType: String? = null,
        @PluginActionProperty bijlagen: List<Bijlage>? = null,
        @PluginActionProperty resultingVariable: String? = null,
        @PluginActionProperty errorVariable: String? = null,
    ): String {
        // The bijlagen datagrid always submits at least one row; drop rows without an
        // informatieObject so the API isn't sent a blank, invalid attachment.
        val filteredBijlagen = bijlagen?.filter { it.informatieObject.isNotBlank() }?.ifEmpty { null }

        // Same for the isGerelateerdAan datagrid: drop rows without a urn so a blank
        // default row isn't sent as an invalid relation.
        val filteredIsGerelateerdAan = isGerelateerdAan?.filter { it.urn.isNotBlank() }?.ifEmpty { null }

        // handelingsPerspectief is bound as a String because the config UI submits "" when
        // nothing is selected, which cannot be coerced to the enum. Map a blank value to null
        // and anything else to the matching enum, failing loudly on an unknown value.
        val handelingsPerspectiefEnum =
            handelingsPerspectief?.takeIf { it.isNotBlank() }?.let { value ->
                HandelingsPerspectiefEnum.entries.firstOrNull { it.value == value }
                    ?: error("Unknown handelingsPerspectief '$value'")
            }

        val bericht =
            try {
                berichtenApiService.createBericht(
                    baseUrl = URI.create(baseUrl),
                    token = token,
                    bericht =
                        Bericht(
                            onderwerp = onderwerp,
                            berichtTekst = berichtTekst,
                            ontvanger = ontvanger,
                            mijnOverheidBerichtenbox = mijnOverheidBerichtenbox,
                            publicatiedatum = publicatiedatum,
                            referentie = referentie,
                            geopendOp = geopendOp,
                            berichtType = berichtType,
                            isGerelateerdAan = filteredIsGerelateerdAan,
                            handelingsPerspectief = handelingsPerspectiefEnum,
                            einddatumHandelingsTermijn = einddatumHandelingsTermijn,
                            bijlagen = filteredBijlagen,
                        ),
                )
            } catch (ex: RestClientException) {
                // RestClientResponseException covers 4xx/5xx HTTP responses; other
                // RestClientExceptions (e.g. ResourceAccessException on connection refused
                // or timeouts) cover transport-level failures where there is no HTTP status.
                val message =
                    when (ex) {
                        is RestClientResponseException ->
                            "Berichten API request failed with status ${ex.statusCode.value()}: ${ex.responseBodyAsString}"
                        else ->
                            "Berichten API request failed: ${ex.message}"
                    }
                logger.error(ex) { message }
                if (errorVariable == null) throw ex
                execution.setVariable(errorVariable, message)
                return ""
            }

        val urn = bericht.urn ?: error("Bericht was created but the API returned no urn")

        resultingVariable?.let { execution.setVariable(it, urn) }

        logger.info { "Created bericht ${bericht.uuid} with urn $urn" }

        return urn
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}
