package com.ritense.valtimoplugins.openvtb.service

import com.ritense.valtimo.contract.annotation.SkipComponentScan
import com.ritense.valtimoplugins.openvtb.client.BerichtenApiClient
import com.ritense.valtimoplugins.openvtb.domain.Bericht
import com.ritense.valtimoplugins.openvtb.domain.PaginatedBerichtList
import com.ritense.valtimoplugins.openvtb.domain.PatchedBerichtGeopendOp
import org.springframework.stereotype.Service
import java.net.URI
import java.time.OffsetDateTime
import java.util.UUID

@SkipComponentScan
@Service
class BerichtenApiService(
    private val berichtenApiClient: BerichtenApiClient,
) {
    fun getBerichten(
        baseUrl: URI,
        token: String,
        page: Int? = null,
        pageSize: Int? = null,
    ): PaginatedBerichtList = berichtenApiClient.berichtenList(baseUrl, token, page, pageSize)

    fun getBericht(
        baseUrl: URI,
        token: String,
        uuid: UUID,
    ): Bericht = berichtenApiClient.berichtenRetrieve(baseUrl, token, uuid)

    fun createBericht(
        baseUrl: URI,
        token: String,
        bericht: Bericht,
    ): Bericht = berichtenApiClient.berichtenCreate(baseUrl, token, bericht)

    fun markGeopend(
        baseUrl: URI,
        token: String,
        uuid: UUID,
        geopendOp: OffsetDateTime,
    ): Bericht = berichtenApiClient.berichtenPartialUpdate(baseUrl, token, uuid, PatchedBerichtGeopendOp(geopendOp))
}
