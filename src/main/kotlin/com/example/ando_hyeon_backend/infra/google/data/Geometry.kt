package com.example.ando_hyeon_backend.infra.google.data

data class Geometry(
    val location: Location,
    val location_type: String,
    val viewport: Viewport
)
