package com.ritense.valtimoplugins.openvtb.domain

import java.net.URI

data class PaginatedBerichtList(
    val count: Int,
    val next: URI? = null,
    val previous: URI? = null,
    val results: List<Bericht>,
)
