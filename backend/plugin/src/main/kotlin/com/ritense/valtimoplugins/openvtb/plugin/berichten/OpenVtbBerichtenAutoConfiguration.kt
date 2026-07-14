package com.ritense.valtimoplugins.openvtb.plugin.berichten

import com.ritense.plugin.service.PluginService
import com.ritense.valtimoplugins.openvtb.service.OpenVtbBerichtenApiService
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

@AutoConfiguration
class OpenVtbBerichtenAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(OpenVtbBerichtenApiService::class)
    fun openVtbBerichtenApiService(): OpenVtbBerichtenApiService = OpenVtbBerichtenApiService()

    @Bean
    @ConditionalOnMissingBean(OpenVtbBerichtenPluginFactory::class)
    fun berichtenApiPluginFactory(
        pluginService: PluginService,
        openVtbBerichtenApiService: OpenVtbBerichtenApiService,
    ): OpenVtbBerichtenPluginFactory = OpenVtbBerichtenPluginFactory(
        pluginService = pluginService,
        openVtbBerichtenApiService = openVtbBerichtenApiService
    )
}
