package com.ritense.valtimoplugins.openvtb.plugin.berichten

import com.ritense.plugin.service.PluginService
import com.ritense.valtimoplugins.openvtb.client.BerichtenApiClient
import com.ritense.valtimoplugins.openvtb.service.BerichtenApiService
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

@AutoConfiguration
class BerichtenAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(BerichtenApiClient::class)
    fun berichtenApiClient(): BerichtenApiClient = BerichtenApiClient()

    @Bean
    @ConditionalOnMissingBean(BerichtenApiService::class)
    fun berichtenApiService(berichtenApiClient: BerichtenApiClient): BerichtenApiService =
        BerichtenApiService(berichtenApiClient)

    @Bean
    @ConditionalOnMissingBean(BerichtenApiPluginFactory::class)
    fun berichtenApiPluginFactory(
        pluginService: PluginService,
        berichtenApiService: BerichtenApiService,
    ): BerichtenApiPluginFactory = BerichtenApiPluginFactory(pluginService, berichtenApiService)
}
