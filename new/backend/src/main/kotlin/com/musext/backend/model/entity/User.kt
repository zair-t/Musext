package com.musext.backend.model.entity

import jakarta.persistence.*
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_user_id_seq")
    @SequenceGenerator(name = "api_user_id_seq", allocationSize = 1)
    var id: Long = 0,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var surname: String,

    @Column(nullable = false)
    var login: String,

    @Column(nullable = false)
    var email: String = "",

    @Column(nullable = false)
    var age: Int,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var gender: String,

//    @OneToMany(mappedBy = "musicUser")
//    @LazyCollection(LazyCollectionOption.TRUE)
//    var musics: Set<Music> = setOf(),
//
//    @OneToMany(mappedBy = "textUser")
//    @LazyCollection(LazyCollectionOption.TRUE)
//    var texts: Set<Text> = setOf(),
)