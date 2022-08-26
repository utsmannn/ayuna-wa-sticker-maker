package com.utsman.ayunastickermaker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AyunaStickerMakerApplication

fun main(args: Array<String>) {
    Schedulers.start()
    runApplication<AyunaStickerMakerApplication>(*args)
}
