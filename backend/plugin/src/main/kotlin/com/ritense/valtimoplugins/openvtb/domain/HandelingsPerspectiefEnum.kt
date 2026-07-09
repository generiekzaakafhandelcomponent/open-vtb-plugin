package com.ritense.valtimoplugins.openvtb.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class HandelingsPerspectiefEnum(
    @JsonValue val value: String,
) {
    NOTSET(""),
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
