package com.example.recyclermulti.models.req

data class Loginreq(

    val phone: String,
    val password: String,
    val device_token: String,
    val device_type: String
)