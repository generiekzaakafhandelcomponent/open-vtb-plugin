package com.ritense.valtimoplugins.openvtb.domain

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.OffsetDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PatchedBerichtGeopendOp(
    /** Tijdstip waarop het bericht door de geadresseerde is geopend. */
    val geopendOp: OffsetDateTime? = null,
)
