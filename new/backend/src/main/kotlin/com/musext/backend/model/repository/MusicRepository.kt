package com.musext.backend.model.repository

import com.musext.backend.model.entity.Music
import com.musext.backend.model.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MusicRepository: CrudRepository<Music, Long> {
    fun findAllByMusicUser(user: User): Iterable<Music>
}