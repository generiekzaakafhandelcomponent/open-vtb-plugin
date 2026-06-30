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

package com.ritense.valtimoplugins.berichtenapi.plugin

import com.ritense.plugin.annotation.Plugin
import com.ritense.plugin.annotation.PluginAction
import com.ritense.plugin.annotation.PluginActionProperty
import com.ritense.plugin.annotation.PluginProperty
import com.ritense.processlink.domain.ActivityTypeWithEventName.SERVICE_TASK_START
import com.ritense.valtimoplugins.berichtenapi.client.Bericht
import com.ritense.valtimoplugins.berichtenapi.client.BerichtenApiService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.operaton.bpm.engine.delegate.DelegateExecution
import java.net.URI

private val logger = KotlinLogging.logger {}

/**
 * Plugin for the Berichten API. The [baseUrl] points at the API root
 * (e.g. `https://example.com/berichten/api/v1`) and [token] is the API token
 * sent as `Authorization: Token <token>`.
 */
@Plugin(
    key = "berichten-api",
    title = "Berichten API",
    description = "Registreer en raadpleeg berichten via de Berichten API.",
)
open class BerichtenApiPlugin(
    private val berichtenApiService: BerichtenApiService,
) {
    @PluginProperty(key = "baseUrl", secret = false)
    lateinit var baseUrl: String

    @PluginProperty(key = "token", secret = true)
    lateinit var token: String

    /**
     * Creates a Bericht and stores its UUID and URL on the process.
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
        @PluginActionProperty berichtTekst: String,
        @PluginActionProperty ontvanger: String,
        @PluginActionProperty mijnOverheidBerichtenbox: Boolean,
    ) {
        val bericht =
            berichtenApiService.createBericht(
                baseUrl = URI.create(baseUrl),
                token = token,
                bericht =
                    Bericht(
                        onderwerp = onderwerp,
                        berichtTekst = berichtTekst,
                        ontvanger = ontvanger,
                        mijnOverheidBerichtenbox = mijnOverheidBerichtenbox,
                    ),
            )

        logger.info { "Created bericht ${bericht.uuid}" }
        execution.setVariable("berichtUuid", bericht.uuid?.toString())
        execution.setVariable("berichtUrl", bericht.url?.toString())
    }
}