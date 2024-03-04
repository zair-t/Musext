package com.example.demo_upload

import com.example.demo_upload.Service.UploadFileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoUploadApplication:CommandLineRunner {
    @Autowired
    lateinit var uploadFileService: UploadFileService

    override fun run(vararg args: String?) {
        uploadFileService.init()
            .onFailure { throw RuntimeException("System cannot start up because no uploads folder is set up") }
    }
}






fun main(args: Array<String>) {
    runApplication<DemoUploadApplication>(*args)
}
