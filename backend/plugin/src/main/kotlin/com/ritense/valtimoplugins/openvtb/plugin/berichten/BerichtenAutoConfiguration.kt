package com.ritense.valtimoplugins.openvtb.plugin.berichten

import com.ritense.plugin.service.PluginService
import com.ritense.valtimoplugins.openvtb.service.BerichtenApiService
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

@AutoConfiguration
class BerichtenAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(BerichtenApiService::class)
    fun berichtenApiService(): BerichtenApiService = BerichtenApiService()

    @Bean
    @ConditionalOnMissingBean(BerichtenApiPluginFactory::class)
    fun berichtenApiPluginFactory(
        pluginService: PluginService,
        berichtenApiService: BerichtenApiService,
    ): BerichtenApiPluginFactory = BerichtenApiPluginFactory(pluginService, berichtenApiService)
}
