package com.cbr.ilk.camundapostgreskotlin.model

import java.util.*

data class Certificate (
    var certificate_id: UUID,
    var wallet_id: UUID,
    var blob: String? = null,
)