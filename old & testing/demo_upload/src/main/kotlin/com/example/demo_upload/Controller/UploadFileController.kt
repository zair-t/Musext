package com.example.demo_upload.Controller

import com.example.demo_upload.Data.Response
import com.example.demo_upload.Service.UploadFileService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/")
class UploadFileController {

    private val log = KotlinLogging.logger {}

    // 1) We inject the Service dependency
    @Autowired
    lateinit var uploadFileService: UploadFileService


    // 2) We define the HTTP Post operation with the file as parameter
    @PostMapping("/upload-file")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<Response> {

        log.info { "Requested uploading file: ${file.originalFilename}" }

        // It invokes the service operation upload and depending on if it went well
        // the file is stored on the file system or an error message is sent back to the user
        return when(uploadFileService.upload(file).getOrDefault(false)) {
            true -> ResponseEntity.status(HttpStatus.OK).body(
                Response(message = "File upload successfully: ${file.originalFilename}"))
            else -> ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                Response(message = "Error when trying to upload the file: ${file.originalFilename} !"))
        }
    }

    //3) We define the HTTP Get method to serve the file or a message indicating the file doesn't exist
    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    fun getFile(@PathVariable filename: String): ResponseEntity<out Any> {
        log.info { "Requested downloading the file: $filename" }

        return when (val res = uploadFileService.download(filename).getOrNull()) {
            is Resource -> ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${filename}")
                .body<Resource>(res)

            else -> ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(Response(message = "Error when trying to download the requested resource: $filename !"))
        }
    }
}