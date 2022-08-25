package com.utsman.ayunastickermaker.controller

import com.utsman.ayunastickermaker.services.WaBotServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
class AyunaController {

    @Autowired
    private lateinit var waBotServices: WaBotServices

    @GetMapping("/ping")
    fun ping(): Any {
        return mapOf("ping" to "ok")
    }

    @GetMapping("/qr", produces = ["text/plain;charset=UTF-8"])
    suspend fun qr(): Any {
        return waBotServices.startWaBot()
    }

    @GetMapping("/start")
    suspend fun start(): ModelAndView {
        val apiUrl = "https://api.whatsapp.com/send?phone=15645448371"
        return ModelAndView("redirect:$apiUrl")
    }
}