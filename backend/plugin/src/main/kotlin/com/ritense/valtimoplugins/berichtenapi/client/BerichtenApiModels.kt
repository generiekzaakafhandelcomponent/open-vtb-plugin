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

package com.ritense.valtimoplugins.berichtenapi.client

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonValue
import java.net.URI
import java.time.OffsetDateTime
import java.util.UUID

/**
 * Data classes generated from `openapi.yaml` (Berichten API 0.1.0).
 *
 * Server-set, read-only fields (`url`, `urn`, `uuid`) are nullable so the same
 * type can be used both as a create payload and as a response. `@JsonInclude` is
 * applied so null/unset fields are omitted from request bodies.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Bericht(
    /** De unieke URL van het Bericht binnen deze API. Read-only. */
    val url: URI? = null,
    /** De Uniform Resource Name van het Bericht. Read-only. */
    val urn: String? = null,
    /** Unieke identificatiecode (UUID4) voor het Bericht. Read-only. */
    val uuid: UUID? = null,
    /** Onderwerp van het bericht. */
    val onderwerp: String,
    /** Tekst van het bericht. */
    val berichtTekst: String,
    /** Datum/tijd waarop bericht zichtbaar moet worden voor de ontvanger. */
    val publicatiedatum: OffsetDateTime? = null,
    /** Eigen optionele referentiegegevens, maximaal 25 tekens. */
    val referentie: String? = null,
    /** URN van een NATUURLIJK PERSOON of NIET-NATUURLIJK PERSOON. */
    val ontvanger: String,
    /** Tijdstip waarop het bericht door de geadresseerde is geopend. */
    val geopendOp: OffsetDateTime? = null,
    /** Code voor het technisch identificeren van een bericht soort & origine. */
    val berichtType: String? = null,
    /** URN's naar de ZAAK of het PRODUCT. */
    val isGerelateerdAan: List<IsGerelateerdAan>? = null,
    /** De door de toegewezen persoon of bedrijf uit te voeren handeling. */
    val handelingsPerspectief: HandelingsPerspectiefEnum? = null,
    /** Datum/tijd waarop handeling afgerond moet zijn. */
    val einddatumHandelingsTermijn: OffsetDateTime? = null,
    /** Geeft aan of dit bericht geschikt is voor publicatie in de MijnOverheid Berichtenbox. */
    val mijnOverheidBerichtenbox: Boolean,
    /** Lijst van bijlagen bij het bericht. */
    val bijlagen: List<Bijlage>? = null,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Bijlage(
    /** URN naar het ENKELVOUDIGINFORMATIEOBJECT. */
    val informatieObject: String,
    /** Goed leesbare omschrijving van de bijlage. */
    val omschrijving: String? = null,
    /** Geeft aan of dit document een standaardbijlage is. */
    val isBerichtTypeBijlage: Boolean = false,
)

data class IsGerelateerdAan(
    /** URN naar de ZAAK of het PRODUCT. */
    val urn: String,
)

data class PaginatedBerichtList(
    val count: Int,
    val next: URI? = null,
    val previous: URI? = null,
    val results: List<Bericht>,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PatchedBerichtGeopendOp(
    /** Tijdstip waarop het bericht door de geadresseerde is geopend. */
    val geopendOp: OffsetDateTime? = null,
)

enum class HandelingsPerspectiefEnum(
    @JsonValue val value: String,
) {
    BETALEN("betalen"),
    INCASSO("incasso"),
    INFORMATIE_GEVEN("informatie_geven"),
    INFORMATIE_KRIJGEN("informatie_krijgen"),
    REACTIE_ONTVANGEN("reactie_ontvangen"),
    VERNIEUWING_NODIG("vernieuwing_nodig"),
    UITNODIGING_VOOR_AFSPRAAK("uitnodiging_voor_afspraak"),
    ;

    companion object {
        /** Maps the API value to an enum, treating the blank/unknown value as null. */
        @JvmStatic
        @JsonCreator
        fun fromValue(value: String): HandelingsPerspectiefEnum? = entries.firstOrNull { it.value == value }
    }
}