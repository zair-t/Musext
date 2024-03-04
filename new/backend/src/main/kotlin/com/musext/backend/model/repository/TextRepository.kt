package com.musext.backend.model.repository

import com.musext.backend.model.entity.Text
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TextRepository: CrudRepository<Text, Long> {
}