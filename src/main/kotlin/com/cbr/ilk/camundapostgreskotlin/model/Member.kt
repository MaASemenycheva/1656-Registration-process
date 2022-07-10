package com.cbr.ilk.camundapostgreskotlin.model

import java.util.UUID

data class Member (
    var member_id: UUID,
    var origin: UUID? = null,
    var name: String? = null,
    var role_id: Int? = null
)


//class Member {
//    var member_id: UUID? = null
//    var origin: UUID? = null
//    var name: String? = null
//    var role_id: Int? = null
//
//
//    constructor() {}
//    constructor(member_id: UUID?, origin: UUID?, name: String?, role_id: Int?) {
//        this.member_id = member_id
//        this.origin = origin
//        this.name = name
//        this.role_id = role_id
//    }
//
//    override fun toString(): String {
//        return "Record{" +
//                "uuid=" + member_id +
//                ", payload='" + origin + '\'' +
//                ", created=" + name +
//                ", processed=" + role_id +
//                '}'
//    }
//}