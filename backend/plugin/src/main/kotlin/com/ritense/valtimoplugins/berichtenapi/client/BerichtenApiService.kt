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

import com.ritense.valtimo.contract.annotation.SkipComponentScan
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