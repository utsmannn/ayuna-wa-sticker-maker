package com.utsman.ayunastickermaker

import java.net.URL
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object Schedulers {

    private val scheduler = Executors.newScheduledThreadPool(1)
    fun start() {
        scheduler.scheduleWithFixedDelay({
            println(" --- start trigger ---")
            val url = URL("https://ayuna-sticker.herokuapp.com/ping")
            val connection = url.openConnection()
            connection.connect()
            println(" --- end trigger ---")
        }, 2, 2, TimeUnit.MINUTES)
    }
}