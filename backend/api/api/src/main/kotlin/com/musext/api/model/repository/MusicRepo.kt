package com.musext.api.model.repository

import com.musext.api.model.entity.Music
import org.springframework.data.repository.CrudRepository

interface MusicRepo: CrudRepository<Music, Long>