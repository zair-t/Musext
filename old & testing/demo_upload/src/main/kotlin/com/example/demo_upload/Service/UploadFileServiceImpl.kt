package com.example.demo_upload.Service

import mu.KotlinLogging
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.isRegularFile

@Service
class UploadFileServiceImpl: UploadFileService {

    //1) Get an instance of the logger
    private val log = KotlinLogging.logger {}

    //2) Define the folder within the project where the files will be stored
    private val uploadsFolderPath: Path = Paths.get("resources/uploads/unsecure")

    //3) This method creates a folder on the project
    override fun init(): Result<Path> =
        runCatching {
            Files.createDirectories(uploadsFolderPath)
        }
            .onFailure { log.error { "Error creating uploads folder: ${it.message}" } }
            .onSuccess { log.info { "Created folder uploads successfully: ${it.fileName}" } }

    //4) This method takes the multipartFile passed as argument
    //  and stores it into the folder
    override fun upload(file: MultipartFile): Result<Boolean> =
        runCatching {
            log.debug { "uploading file ${file.originalFilename} to ${uploadsFolderPath.fileName} folder"}

            val uploadedTargetFilePath = uploadsFolderPath.resolve(file.originalFilename);
            Files.copy(file.inputStream, uploadedTargetFilePath)
            uploadedTargetFilePath.isRegularFile()
        }.onFailure {
            log.warn { "Error uploading file ${file.originalFilename} reason: ${it.javaClass}" }}

    //5) This method will serve the files to download
    override fun download(filename:String):Result<Resource> =
        runCatching {
            log.debug { "Downloading file $filename from ${uploadsFolderPath.fileName} folder"}
            val file: Path = uploadsFolderPath.resolve(filename)

            val resource: Resource = UrlResource(file.toUri())
            if (resource.exists() || resource.isReadable)
                resource
            else throw FileNotFoundException("The file does not exist or is not readable!")
        }.onFailure {
            log.warn { "Error downloading file $filename reason: ${it.javaClass}" }}

}