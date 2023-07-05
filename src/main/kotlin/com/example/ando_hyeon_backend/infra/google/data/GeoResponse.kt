package com.example.ando_hyeon_backend.infra.google.data

data class GeoResponse(
    val address_components: List<AddressComponent>,
    val formatted_address: String,
    val geometry: Geometry,
    val place_id: String,
    val types: List<String>
)
