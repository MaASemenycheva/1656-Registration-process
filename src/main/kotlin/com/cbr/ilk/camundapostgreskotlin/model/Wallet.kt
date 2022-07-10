package com.cbr.ilk.camundapostgreskotlin.model

import java.math.BigDecimal
import java.util.*

data class Wallet (
    var wallet_id: UUID,
    var origin: UUID? = null,
    var name: String? = null,
    var member_id: UUID? = null,
    var type_id: Int? = null,
    var status_id: Int? = null,
    var balance: BigDecimal? = null,
)