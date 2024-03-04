package com.musext.api.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Lob

@Entity
class Music(
    @Column(nullable = false, length = 1023)
    var name: String,

    @Column(nullable = false, length = 1023)
    var author: String,

    @Column(columnDefinition = "BYTEA")
    var image: ByteArray,

    @Column(columnDefinition = "BYTEA")
    var music: ByteArray
): AbstractEntity()