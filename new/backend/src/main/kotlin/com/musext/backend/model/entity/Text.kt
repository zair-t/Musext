package com.musext.backend.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "text")
data class Text(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "text_id_seq")
    @SequenceGenerator(name = "text_id_seq", allocationSize = 1)
    var id: Long = 0,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var body: String,

    @Column(nullable = false)
    var createdAt: String,

    @ManyToOne
    var textUser: User
)