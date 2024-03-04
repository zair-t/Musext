package com.example.demo_upload.Service

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path

interface UploadFileService {

    //1) This method will create the folder needed to
    // store the files
    fun init(): Result<Path>

    //2) This method will allow upload files
    fun upload(file: MultipartFile): Result<Boolean>

    //3) This method will serve the files to download
    fun download(filename: String): Result<Resource>
}