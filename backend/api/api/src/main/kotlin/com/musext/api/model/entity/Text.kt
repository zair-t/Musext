package com.musext.api.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Lob

@Entity
class Text(
    @Column(nullable = false, length = 1023)
    var title: String,

    @Column(columnDefinition="TEXT")
    var content: String
): AbstractEntity()