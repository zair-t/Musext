package com.musext.api.model.entity

import jakarta.persistence.*

@Entity
class Role(@Column var name: String) : AbstractEntity() {}