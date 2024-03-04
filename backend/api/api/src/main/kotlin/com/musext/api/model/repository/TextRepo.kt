package com.musext.api.model.repository

import com.musext.api.model.entity.Text
import org.springframework.data.repository.CrudRepository

interface TextRepo: CrudRepository<Text, Long>