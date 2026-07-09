package com.ritense.valtimoplugins.openvtb.domain

import com.fasterxml.jackson.annotation.JsonInclude


/**
 * Data classes generated from `openapi.yaml` (Berichten API 0.1.0).
 *
 * Server-set, read-only fields (`url`, `urn`, `uuid`) are nullable so the same
 * type can be used both as a create payload and as a response. `@JsonInclude` is
 * applied so null/unset fields are omitted from request bodies.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Bijlage(
    /** URN naar het ENKELVOUDIGINFORMATIEOBJECT. */
    val informatieObject: String,
    /** Goed leesbare omschrijving van de bijlage. */
    val omschrijving: String? = null,
    /** Geeft aan of dit document een standaardbijlage is. */
    val isBerichtTypeBijlage: Boolean = false,
)
