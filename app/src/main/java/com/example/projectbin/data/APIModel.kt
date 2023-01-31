package com.example.projectbin.data


var NullText = "No Info"
data class NumberType(
    val length: String = NullText,
    val luhn: Boolean? = null,
)

data class CountryType  (
    val numeric: String = NullText,
    val alpha2: String? = NullText,
    val name: String? = NullText,
    val emoji: String? = null,
    val currency: String? = NullText,
    val latitude: String? = NullText,
    val longitude: String? = NullText,
)

data class BankType(
    val name: String? = NullText,
    val url: String? = NullText,
    val phone: String? = NullText,
    val city: String? = NullText,
)

data class APIModel(
    val number: NumberType? = null,
    val scheme: String? = NullText,
    val type: String? = NullText,
    val brand: String = NullText,
    val prepaid: Boolean? = null,
    val country: CountryType? = null,
    val bank: BankType? = null,
    )