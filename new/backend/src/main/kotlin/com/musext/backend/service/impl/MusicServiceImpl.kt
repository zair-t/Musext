package com.musext.backend.service.impl

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.musext.backend.model.dto.request.MusicRequest
import com.musext.backend.model.entity.Music
import com.musext.backend.model.repository.MusicRepository
import com.musext.backend.model.repository.UserRepository
import com.musext.backend.service.MusicService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.ByteBuffer

@Service
class MusicServiceImpl: MusicService {
    @Autowired
    private lateinit var musicRepository: MusicRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    private val s3client: AmazonS3

    init {
        val credentials = BasicAWSCredentials(
            "YCAJEXmM2vDn_9dm7wm0scuY4",
            "YCPXzrTj81kLaFL47IvZVKcFxsHu_MX4XnpKpyBE"
        )
        s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(
                    "storage.yandexcloud.net", "ru-central1"
                )
            ).build()
    }

    override fun findByUserId(userId: Long): List<Music> {
        return musicRepository.findAllByMusicUser(userRepository.findById(userId).get()).toList()
    }

    override fun findAll(): List<Music> {
        return musicRepository.findAll().reversed().toList()
    }

    override fun save(musicRequest: MusicRequest): Music {
        val userId = musicRequest.userId
        val file = musicRequest.file
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = file.contentType
        objectMetadata.contentLength = file.size
        println("" + userId + file.originalFilename + file.contentType)
        s3client.putObject("1project1", file.originalFilename, file.inputStream, objectMetadata)
        return musicRepository.save(
            Music(
                uri = "https://1project1.storage.yandexcloud.net/${file.originalFilename}",
                musicUser = userRepository.findById(userId).get()
            ))
    }
}