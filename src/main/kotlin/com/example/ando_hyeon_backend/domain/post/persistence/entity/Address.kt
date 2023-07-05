package com.example.ando_hyeon_backend.domain.post.persistence.entity

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Address (
    detailAddress: String,
    lat: Double = 0.0,
    long: Double = 0.0
) {
    @Column(name = "detail_address")
    val detailAddress: String = detailAddress
    val latitude: Double = lat
    val longitude: Double = long
}
