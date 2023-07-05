package com.example.ando_hyeon_backend.infra.google

import com.example.ando_hyeon_backend.infra.google.data.GeoResponse

interface GeoService {

    fun getLatitudeLongitudeByAddress(lat: Double, long: Double): List<GeoResponse>
}
