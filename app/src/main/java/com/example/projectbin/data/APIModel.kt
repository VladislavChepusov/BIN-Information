package com.example.projectbin.data

data class NumberType(
    val length: String? = null,
    val luhn: String? = null,
)

data class CountryType  (
    val numeric: String? = null,
    val alpha2: String? = null,
    val emoji: String? = null,
    val currency: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
)

data class BankType(
    val name: String? = null,
    val url: String? = null,
    val phone: String? = null,
    val city: String? = null,
)

data class APIModel(
    val number: NumberType? = null,
    val scheme: String? = null,
    val type: String? = null,
    val brand: String? = null,
    val prepaid: String? = null,
    val country: CountryType? = null,
    val bank: BankType? = null,
    )