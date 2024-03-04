package com.musext.backend.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "musics")
data class Music(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "music_id_seq")
    @SequenceGenerator(name = "music_id_seq", allocationSize = 1)
    var id: Long = 0,

    @Column(nullable = false)
    var uri: String,

    @ManyToOne
    var musicUser: User
)