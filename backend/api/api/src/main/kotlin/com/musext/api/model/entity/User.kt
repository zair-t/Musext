package com.musext.api.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "\"user\"")
class User(
    @Column var name: String,
    @Column var username: String,
    @Column var password: String
) : AbstractEntity() {
    @ManyToMany(fetch = FetchType.EAGER)
    var roles: Collection<Role> = ArrayList()
}